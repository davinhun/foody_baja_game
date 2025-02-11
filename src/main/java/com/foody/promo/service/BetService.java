package com.foody.promo.service;

import com.foody.promo.config.constants.ResultTypes;
import com.foody.promo.domain.BetModel;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.BetEntity;
import com.foody.promo.entity.MatchEntity;
import com.foody.promo.entity.UserEntity;
import com.foody.promo.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.foody.promo.config.constants.EventTypes.EVENT_FINISHED;
import static com.foody.promo.utils.Utils.getUser;
import static com.foody.promo.utils.Utils.getUserId;

@Service
public class BetService {

    @Autowired
    private BetRepository betRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ConversionService conversionService;

    public Optional<BetModel> getByMatchId(Long matchId) {
        return betRepository.findByMatchIdAndAppUserId(matchId, getUserId())
                .map(this::convertUserAndHideFields);
    }

    public List<BetModel> getAllByUserId(Long userId) {
        return betRepository.findAllByAppUserId(userId)
                .stream()
                .map(this::convertUserAndHideFields)
                .collect(Collectors.toList());
    }


    @Transactional
    public void saveMyBet(BetModel betModel) {
        betModel.setPossibleWinning(calculateWinning(betModel, betModel.getMatch()));

        betRepository.save(convertToEntityToSaveMyBet(betModel));
        userService.updateAvailablePointForUser(betModel);
    }

    @Transactional
    public void saveAndUpdateBets(MatchModel matchModel) {
        List<BetModel> betModels = getAllByMatchId(matchModel.getId());

        List<UserModel> userModels = new ArrayList<>();
        List<BetEntity> entities = betModels.stream()
                .peek(bet -> {
                    bet.setWinning(fillWinning(bet, matchModel));
                    bet.setPossibleWinning(null);

                    UserModel userModel = bet.getUser();
                    userModel.setAvailablePoints(userModel.getAvailablePoints() + bet.getWinning());
                    userModel.setLastEvent(EVENT_FINISHED + ": " + bet.getMatch().getId());
                    userModel.setLastChange(bet.getWinning());

                    userModels.add(userModel);
                })
                .map(model -> conversionService.convert(model, BetEntity.class))
                .collect(Collectors.toList());

        betRepository.saveAll(entities);
        userService.saveAll(userModels);
    }

    private List<BetModel> getAllByMatchId(Long matchId) {
        return betRepository.findAllByMatchId(matchId).stream()
                .map(entity -> conversionService.convert(entity, BetModel.class))
                .collect(Collectors.toList());
    }

    private BetModel convertUserAndHideFields(BetEntity entity) {
        BetModel model = conversionService.convert(entity, BetModel.class);
        model.getUser().hideFields(true);

        return model;
    }

    private Long fillWinning(BetModel myBet, MatchModel matchModel) {
        //Wrong guess
        if (!matchModel.getResult().equals(myBet.getGuess())) {
            return 0L;
        }
        return calculateWinning(myBet, matchModel);
    }

    private Long calculateWinning(BetModel myBet, MatchModel matchModel) {
        switch (myBet.getGuess()) {
            case ResultTypes.TEAM1:
                return Math.round(matchModel.getOddsTeam1() * myBet.getPoint());
            case ResultTypes.DRAW:
                return Math.round(matchModel.getOddsDraw() * myBet.getPoint());
            case ResultTypes.TEAM2:
                return Math.round(matchModel.getOddsTeam2() * myBet.getPoint());
        }
        throw new IllegalArgumentException();
    }

    private BetEntity convertToEntityToSaveMyBet(BetModel model) {
        return BetEntity.builder()
                .id(model.getId())
                .match(conversionService.convert(model.getMatch(), MatchEntity.class))
                .appUser(conversionService.convert(getUser(), UserEntity.class))
                .guess(model.getGuess())
                .point(model.getPoint())
                .winning(model.getWinning())
                .possibleWinning(model.getPossibleWinning())
                .build();
    }
}
