package com.foody.promo.controller;

import com.foody.promo.domain.UserModel;
import com.foody.promo.service.LoginRegisterService;
import com.foody.promo.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static com.foody.promo.config.path.PathConfig.*;
import static com.foody.promo.service.LoginRegisterService.HASH;
import static com.foody.promo.service.LoginRegisterService.USER_ID;

@Controller
public class LoginRegisterController {

    @Autowired
    private LoginRegisterService loginRegisterService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/" + REGISTER)
    public String getRegForm() {
        return "auth/" + REGISTER;
    }

    @PostMapping(value = "/" + REGISTER)
    public String registerUser(@Valid @ModelAttribute UserModel userModel, Model model, BindingResult bindingResult) {
        userValidator.validate(userModel, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingErrors", bindingResult.getAllErrors());
            return "auth/" + REGISTER;
        }

        loginRegisterService.registerUser(userModel);

        model.addAttribute("regSuccess", true);

        return "auth/" + LOGIN;
    }

    @GetMapping(value = "/" + REGISTER + "/" + CONFIRMATION)
    public String confirmEmail(@Valid @RequestParam Map<String, String> params, Model model) {
        boolean emailConfirm = loginRegisterService.successFullEmailConfirm(params);

        model.addAttribute("emailConfirmed", emailConfirm);

        return "auth/" + LOGIN;
    }

    @PostMapping(value = "/" + REGISTER + "/" + SEND_PASSWORD_RESET)
    public String postPasswordResetRequest(@Valid @RequestParam @NotNull String emailAddress, Model model) {
        boolean resetRequest = loginRegisterService.resetRequest(emailAddress);

        model.addAttribute("resetRequest", resetRequest);

        return "auth/" + LOGIN;
    }

    @GetMapping(value = "/" + REGISTER + "/" + RECEIVE_PASSWORD_RESET)
    public String receivePasswordResetRequest(@Valid @RequestParam Map<String, String> params, Model model) {
        model.addAttribute(USER_ID, params.get(USER_ID));
        model.addAttribute(HASH, params.get(HASH));

        return "auth/" + NEW_PASSWORD;
    }

    @PostMapping(value = "/" + REGISTER + "/" + CHANGE_PASSWORD)
    public String changePassword(@Valid @RequestParam Map<String, String> params, Model model) {
        boolean passwordChanged = loginRegisterService.changePassword(params);

        model.addAttribute("passwordChanged", passwordChanged);

        return "auth/" + LOGIN;
    }
}
