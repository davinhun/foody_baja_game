package com.foody.promo.converter.match;

import com.foody.promo.converter.team.TeamEntityToTeamModelConverter;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.entity.MatchEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class MatchEntityToMatchModelConverter implements Converter<MatchEntity, MatchModel> {

    private final TeamEntityToTeamModelConverter converter;

    @Override
    public MatchModel convert(MatchEntity entity) {
        return new MatchModel(entity.getId(), converter.convert(entity.getTeam1()),
                converter.convert(entity.getTeam2()),
                entity.getStartDate(), entity.getResult(), entity.getNumericResult(), entity.getOddsTeam1(),
                entity.getOddsDraw(), entity.getOddsTeam2(), entity.getStatus(), entity.getGroup());

    }
}
