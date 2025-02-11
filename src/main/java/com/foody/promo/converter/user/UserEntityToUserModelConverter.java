package com.foody.promo.converter.user;

import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityToUserModelConverter implements Converter<UserEntity, UserModel> {


    @Override
    public UserModel convert(UserEntity entity) {
        return new UserModel(entity.getId(), entity.getOauthId(), entity.getName(), entity.getEmail(),
                entity.getAvailablePoints(), entity.getLastEvent(), entity.getLastChange(),
                entity.getPassword(), entity.getRoles(), entity.getEmailConfirmed(), entity.isBanned());
    }
}
