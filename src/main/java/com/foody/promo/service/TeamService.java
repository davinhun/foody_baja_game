package com.foody.promo.service;

import com.foody.promo.domain.TeamModel;
import com.foody.promo.entity.TeamEntity;
import com.foody.promo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ConversionService conversionService;

    public List<TeamModel> getAll() {
        return StreamSupport.stream(teamRepository.findAll().spliterator(), false)
                .map(entity -> conversionService.convert(entity, TeamModel.class))
                .collect(toList());
    }

    public Optional<TeamModel> getByName(String name) {
        return teamRepository.findByName(name).map(entity -> conversionService.convert(entity, TeamModel.class));
    }

    public Optional<TeamModel> getById(Long id) {
        return teamRepository.findById(id).map(entity -> conversionService.convert(entity, TeamModel.class));
    }

    public Optional<TeamModel> getByNameAndIdNot(String name, Long id) {
        return teamRepository.findByNameAndIdNot(name, id).map(entity -> conversionService.convert(entity, TeamModel.class));
    }

    @Transactional
    public void save(TeamModel model) {
        teamRepository.save(conversionService.convert(model, TeamEntity.class));
    }

}
