package com.foody.promo.converter.user;

import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserModelToUserEntityConverter implements Converter<UserModel, UserEntity> {


    @Override
    public UserEntity convert(UserModel model) {
        return UserEntity.builder()
                .id(model.getId())
                .oauthId(model.getOauthId())
                .name(model.getName())
                .email(model.getEmail())
                .availablePoints(model.getAvailablePoints())
                .lastEvent(model.getLastEvent())
                .lastChange(model.getLastChange())
                .password(model.getPassword())
                .roles(model.getRoles())
                .emailConfirmed(model.getEmailConfirmed())
                .banned(model.getBanned())
                .build();
    }
}
