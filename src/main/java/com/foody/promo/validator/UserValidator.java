package com.foody.promo.validator;

import com.foody.promo.domain.UserModel;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors err) {
        UserModel userModel = (UserModel) obj;
        Optional<UserModel> userByEmail = userService.getByEmail(userModel.getEmail());

        if (userByEmail.isPresent() && userByEmail.get().getBanned()) {
            err.rejectValue("", "", "");
        }
        else if (userByEmail.isPresent()) {
            err.rejectValue("email", "exists", "Already registered with this email");
        }
    }
}

