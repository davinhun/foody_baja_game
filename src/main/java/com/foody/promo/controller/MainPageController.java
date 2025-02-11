package com.foody.promo.controller;

import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.foody.promo.config.constants.Parameters.POINT_AND_RANK;
import static com.foody.promo.config.path.PathConfig.HOW_TO_PLAY;
import static com.foody.promo.config.path.PathConfig.MAIN;
import static com.foody.promo.utils.Utils.getUserId;

@Controller
public class MainPageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String getMainPage(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));

        return MAIN;
    }

    @GetMapping("/" + HOW_TO_PLAY)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public String howToPlay(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));

        return HOW_TO_PLAY;
    }
}
