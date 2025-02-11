package com.foody.promo.service;

import com.foody.promo.domain.BetModel;
import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.domain.PointAndRankingModel;
import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.UserEntity;
import com.foody.promo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.foody.promo.config.constants.EventTypes.BET_ON_MATCH;
import static com.foody.promo.config.constants.EventTypes.INVOICE_UPLOAD;
import static com.foody.promo.schedule.RankingJob.USER_RANKING;
import static com.foody.promo.utils.Utils.getUserId;

@Service
public class UserService {

    public static final Sort SORT_BY_AVAILABLE_POINTS_DSC = Sort.by(Sort.Direction.DESC, "availablePoints");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversionService conversionService;

    public void save(UserModel userModel) {
        userRepository.save(conversionService.convert(userModel, UserEntity.class));
    }

    @Transactional
    public void saveAll(List<UserModel> userModels) {
        List<UserEntity> entities = userModels.stream()
                .map(entity -> conversionService.convert(entity, UserEntity.class))
                .collect(Collectors.toList());
        userRepository.saveAll(entities);
    }

    public UserModel getById(Long id) {
        return userRepository.findById(id)
                .map(entity -> conversionService.convert(entity, UserModel.class))
                .orElseThrow(() -> new ValidationException("User not found for " + id));
    }

    public Optional<UserModel> getByOauthId(Long id) {
        return userRepository.findByOauthId(id).map(e -> conversionService.convert(e, UserModel.class));
    }

    public Optional<UserModel> getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).map(e -> conversionService.convert(e, UserModel.class));
    }

    public void updateAvailablePointForUser(BetModel betModel) {
        UserModel currentUser = getById(getUserId());
        currentUser.setAvailablePoints(currentUser.getAvailablePoints() - betModel.getPoint());
        currentUser.setLastEvent(BET_ON_MATCH + ": " + betModel.getMatch().getId());
        currentUser.setLastChange(-betModel.getPoint());

        save(currentUser);
    }

    public void addPointAfterInvoiceUpload(InvoiceModel invoiceModel) {
        UserModel currentUser = getById(getUserId());
        currentUser.setAvailablePoints(currentUser.getAvailablePoints() + invoiceModel.getPoint());
        currentUser.setLastEvent(INVOICE_UPLOAD);
        currentUser.setLastChange(invoiceModel.getPoint());

        save(currentUser);
    }

    public List<UserModel> getAllOrderByPoint(boolean forUser) {
        return userRepository.findAll(SORT_BY_AVAILABLE_POINTS_DSC).stream()
                .map(entity -> conversionService.convert(entity, UserModel.class))
                .peek(model -> model.hideFields(forUser))
                .collect(Collectors.toList());
    }

    public List<UserModel> getAllNotBannedOrderByPoint(boolean forUser) {
        return userRepository.findAllByBannedIsFalse(SORT_BY_AVAILABLE_POINTS_DSC).stream()
                .map(entity -> conversionService.convert(entity, UserModel.class))
                .peek(model -> model.hideFields(forUser))
                .collect(Collectors.toList());
    }

    public PointAndRankingModel getRankingFor(Long id) {
        Long point = getById(id).getAvailablePoints();
        Long rank = USER_RANKING.get(id);
        return new PointAndRankingModel(point, rank);
    }

}
