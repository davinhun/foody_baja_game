package com.foody.promo.validator;

import com.foody.promo.config.constants.MatchStatus;
import com.foody.promo.config.constants.ResultTypes;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;

import static com.nimbusds.oauth2.sdk.util.StringUtils.isBlank;


@Component
public class MatchValidator {

    @Autowired
    private MatchService matchService;

    public void validate(MatchModel matchModel) {

        if (matchModel.getTeam1().equals(matchModel.getTeam2())) {
            throw new ValidationException("The teams cannot be the same");
        }

        if (matchModel.getId() != null) {
            Optional<MatchModel> matchIdDb = matchService.getById(matchModel.getId());
            if (matchModel.getId() != null && matchIdDb.isEmpty()) {
                throw new ValidationException("Match not exists!");
            }
            MatchModel existingMatch = matchIdDb.get();
            if (matchModel.getId() != null && existingMatch.getStatus().equals(MatchStatus.FINISHED)) {
                throw new ValidationException("Finished match cannot be modified!");
            }
            if (!matchModel.getOddsTeam1().equals(existingMatch.getOddsTeam1())) {
                throw new ValidationException("Team1 odds must not modified!");
            }
            if (!matchModel.getOddsDraw().equals(existingMatch.getOddsDraw())) {
                throw new ValidationException("Draw odds must not modified!");
            }
            if (!matchModel.getOddsTeam2().equals(existingMatch.getOddsTeam2())) {
                throw new ValidationException("Team2 odds must not modified!");
            }
            if (!matchModel.getTeam1().equals(existingMatch.getTeam1())) {
                throw new ValidationException("Team1 team must not modified!");
            }
            if (!matchModel.getTeam2().equals(existingMatch.getTeam2())) {
                throw new ValidationException("Team2 team must not modified!");
            }
        }

        if (isBlank(matchModel.getResult()) && !isBlank(matchModel.getNumericResult()) || !isBlank(matchModel.getResult()) && isBlank(matchModel.getNumericResult())) {
            throw new ValidationException("Result and numericResult must filled together");
        }
        if (matchModel.getId() == null && !matchModel.getStatus().equals(MatchStatus.AVAILABLE)) {
            throw new ValidationException("New match must be in available state");
        }

        boolean resultsFilled = !isBlank(matchModel.getResult());

        if (matchModel.getStatus().equals(MatchStatus.AVAILABLE) && resultsFilled) {
            throw new ValidationException("Result and numericResult must be empty if the status is in available");
        }

        if (matchModel.getStatus().equals(MatchStatus.IN_PROGRESS) && resultsFilled) {
            throw new ValidationException("Result and numericResult must be empty if the status is in progress");
        }

        if (matchModel.getStatus().equals(MatchStatus.FINISHED) && !resultsFilled) {
            throw new ValidationException("Result and numericResult must not empty if the status is finished");
        }

        if (matchModel.getStatus().equals(MatchStatus.FINISHED)) {
            String[] numericResult = matchModel.getNumericResult().split("-");
            long team1Goals = Long.parseLong(numericResult[0]);
            long team2Goals = Long.parseLong(numericResult[1]);

            if (matchModel.getResult().equals(ResultTypes.TEAM1) && team1Goals <= team2Goals) {
                throw new ValidationException("If team 1 won, team 1 goals must be greater than team 2 goals");
            }
            if (matchModel.getResult().equals(ResultTypes.DRAW) && team1Goals != team2Goals) {
                throw new ValidationException("If draw, team 1 goals must be equal with team 2 goals");
            }
            if (matchModel.getResult().equals(ResultTypes.TEAM2) && team1Goals >= team2Goals) {
                throw new ValidationException("If team 2 won, team 2 goals must be greater than team 1 goals");
            }
        }
    }
}

