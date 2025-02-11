package com.foody.promo.converter.team;

import com.foody.promo.domain.TeamModel;
import com.foody.promo.entity.TeamEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.foody.promo.utils.Utils.convertEmptyToNull;

@Component
public class TeamModelToTeamEntityConverter implements Converter<TeamModel, TeamEntity> {

    @Override
    public TeamEntity convert(TeamModel model) {
        return TeamEntity.builder()
                .id(model.getId())
                .name(model.getName())
                .flagLink(convertEmptyToNull(model.getFlagLink()))
                .build();
    }
}
