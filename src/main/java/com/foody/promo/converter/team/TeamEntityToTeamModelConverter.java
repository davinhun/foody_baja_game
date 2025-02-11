package com.foody.promo.converter.team;

import com.foody.promo.domain.TeamModel;
import com.foody.promo.entity.TeamEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TeamEntityToTeamModelConverter implements Converter<TeamEntity, TeamModel> {

    @Override
    public TeamModel convert(TeamEntity entity) {
        return new TeamModel(entity.getId(), entity.getName(), entity.getFlagLink());
    }
}
