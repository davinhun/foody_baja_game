package com.foody.promo.service;

import com.foody.promo.config.constants.MatchStatus;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.entity.MatchEntity;
import com.foody.promo.entity.UserEntity;
import com.foody.promo.repository.MatchRepository;
import com.foody.promo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AdminService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BetService betService;
    @Autowired
    private ConversionService conversionService;

    public List<MatchModel> getAll() {
        return matchRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"))
                .stream()
                .map(entity -> conversionService.convert(entity, MatchModel.class))
                .collect(toList());
    }

    public List<MatchModel> getAllByStatus(String status) {
        return matchRepository.findAllByStatusOrderByStartDate(status).stream()
                .map(entity -> conversionService.convert(entity, MatchModel.class))
                .collect(toList());
    }

    @Transactional
    public void saveAndUpdateWinnings(MatchModel matchModel) {
        if (matchModel.getId() != null && matchModel.getStatus().equals(MatchStatus.FINISHED)) {
            betService.saveAndUpdateBets(matchModel);
        }
        matchRepository.save(conversionService.convert(matchModel, MatchEntity.class));
    }

    @Transactional
    public void saveAll(List<MatchModel> matchModels) {
        List<MatchEntity> entities = matchModels.stream()
                .map(model -> conversionService.convert(model, MatchEntity.class))
                .collect(toList());
        matchRepository.saveAll(entities);
    }

    public void banUser(Long userId) {
        Optional<UserEntity> userById = userRepository.findById(userId);
        UserEntity userEntity = userById.get();
        userEntity.setBanned(!userEntity.isBanned());
        userRepository.save(userEntity);
    }


}
