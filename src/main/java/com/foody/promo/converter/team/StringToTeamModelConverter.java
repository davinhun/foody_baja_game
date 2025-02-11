package com.foody.promo.converter.team;

import com.foody.promo.domain.TeamModel;
import com.foody.promo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;

@Component
public class StringToTeamModelConverter implements Converter<String, TeamModel> {

    @Autowired
    private TeamService teamService;

    @Override
    public TeamModel convert(String teamId) {
        Optional<TeamModel> byId = teamService.getById(Long.valueOf(teamId));
        if (byId.isEmpty()) {
            throw new ValidationException("Not existing id");
        }
        return byId.get();
    }
}
