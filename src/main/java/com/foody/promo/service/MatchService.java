package com.foody.promo.service;

import com.foody.promo.domain.BetModel;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.domain.MatchModelWithBetModel;
import com.foody.promo.entity.MatchEntity;
import com.foody.promo.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.foody.promo.config.constants.MatchStatus.FINISHED;
import static java.util.stream.Collectors.toList;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private BetService betService;
    @Autowired
    private ConversionService conversionService;

    public Optional<MatchModel> getById(Long id) {
        return matchRepository.findById(id)
                .map(entity -> conversionService.convert(entity, MatchModel.class));
    }

    public Optional<MatchModelWithBetModel> getByMatchIdWithMyBet(Long matchId) {
        Optional<MatchEntity> getById = matchRepository.findById(matchId);

        if (getById.isEmpty()) {
            return Optional.empty();
        } else {
            MatchModel matchModel = conversionService.convert(getById.get(), MatchModel.class);
            MatchModelWithBetModel.MatchModelWithBetModelBuilder matchModelWithBetModel = MatchModelWithBetModel.builder().match(matchModel);
            Optional<BetModel> myBet = betService.getByMatchId(matchId);
            myBet.ifPresent(matchModelWithBetModel::bet);

            return Optional.of(matchModelWithBetModel.build());
        }
    }

    public List<MatchModelWithBetModel> getWithBets(List<String> statuses, Long userId) {
        return getMatchesFilledWithBets(statuses, userId);
    }

    public List<MatchModelWithBetModel> getAllFinishedOrBetted(List<String> statuses, Long userId) {
        List<MatchModelWithBetModel> matchModelWithBetModels = getMatchesFilledWithBets(statuses, userId);

        return matchModelWithBetModels.stream()
                .filter(model -> model.getMatch().getStatus().equals(FINISHED) || model.getBet() != null)
                .collect(toList());
    }

    private List<MatchModelWithBetModel> getMatchesFilledWithBets(List<String> statuses, Long userId) {
        List<BetModel> myBets = betService.getAllByUserId(userId);

        return matchRepository.findAllByStatusInOrderByStartDate(statuses).stream()
                .map(entity -> conversionService.convert(entity, MatchModel.class))
                .map(matchModel -> {
                    Optional<BetModel> myBet = myBets.stream().filter(bet -> bet.getMatch().equals(matchModel)).findFirst();
                    MatchModelWithBetModel.MatchModelWithBetModelBuilder matchModelWithBetModel = MatchModelWithBetModel.builder().match(matchModel);
                    myBet.ifPresent(matchModelWithBetModel::bet);

                    return matchModelWithBetModel.build();
                })
                .collect(toList());
    }
}
