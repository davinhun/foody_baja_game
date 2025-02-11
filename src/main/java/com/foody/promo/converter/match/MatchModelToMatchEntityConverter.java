package com.foody.promo.converter.match;

import com.foody.promo.converter.team.TeamModelToTeamEntityConverter;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.entity.MatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

import static com.foody.promo.utils.Utils.convertEmptyToNull;

@RequiredArgsConstructor
public class MatchModelToMatchEntityConverter implements Converter<MatchModel, MatchEntity> {


    private final TeamModelToTeamEntityConverter converter;

    @Override
    public MatchEntity convert(MatchModel model) {
        return MatchEntity.builder()
                .id(model.getId())
                .team1(converter.convert(model.getTeam1()))
                .team2(converter.convert(model.getTeam2()))
                .startDate(model.getStartDate())
                .result(convertEmptyToNull(model.getResult()))
                .numericResult(convertEmptyToNull(model.getNumericResult()))
                .oddsTeam1(model.getOddsTeam1())
                .oddsDraw(model.getOddsDraw())
                .oddsTeam2(model.getOddsTeam2())
                .status(model.getStatus())
                .group(model.getGroup())
                .build();
    }
}
