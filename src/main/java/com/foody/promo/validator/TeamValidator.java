package com.foody.promo.validator;

import com.foody.promo.domain.TeamModel;
import com.foody.promo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;


@Component
public class TeamValidator {

    @Autowired
    private TeamService teamService;

    public void validate(TeamModel teamModel) {

        if (teamModel.getId() == null && teamService.getByName(teamModel.getName()).isPresent()) {
            throw new ValidationException("This name already exists");
        }
        if (teamModel.getId() != null) {
            Optional<TeamModel> byId = teamService.getById(teamModel.getId());
            if (teamModel.getId() != null && byId.isEmpty()) {
                throw new ValidationException("This id not exists");
            }

            if (!teamModel.getName().equals(byId.get().getName())) {
                throw new ValidationException("Team name must not change");
            }
        }

        if (teamModel.getId() != null && teamService.getByNameAndIdNot(teamModel.getName(), teamModel.getId()).isPresent()) {
            throw new ValidationException("This name already exists");
        }
    }
}

