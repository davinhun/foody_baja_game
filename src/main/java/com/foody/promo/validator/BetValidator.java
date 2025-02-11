package com.foody.promo.validator;

import com.foody.promo.config.constants.MatchStatus;
import com.foody.promo.domain.BetModel;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.domain.MatchModelWithBetModel;
import com.foody.promo.service.MatchService;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;

import static com.foody.promo.utils.Utils.getUserId;


@Component
public class BetValidator {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;

    public void validate(BetModel betModel) {

        Optional<MatchModelWithBetModel> matchModelWithBet = matchService.getByMatchIdWithMyBet(betModel.getMatch().getId());

        if (matchModelWithBet.isEmpty()) {
            throw new ValidationException("Match not exists!");
        }
        MatchModel existingMatch = matchModelWithBet.get().getMatch();

        if (matchModelWithBet.get().getBet() != null) {
            throw new ValidationException("You already bet on this match");
        }

        if (!existingMatch.getStatus().equals(MatchStatus.AVAILABLE)) {
            throw new ValidationException("Match already started");
        }
        if (existingMatch.getStartDate() <= System.currentTimeMillis()) {
            throw new ValidationException("Match already started");
        }
        Long maxAvailablePoints = userService.getRankingFor(getUserId()).getPoint();
        if (maxAvailablePoints < betModel.getPoint()) {
            throw new ValidationException("Not enough point");
        }
    }
}

