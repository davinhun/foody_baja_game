package com.foody.promo.controller;

import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.foody.promo.config.constants.Parameters.POINT_AND_RANK;
import static com.foody.promo.config.path.PathConfig.TOPLIST;
import static com.foody.promo.utils.Utils.getUserId;

@Controller
public class ToplistController {

    @Autowired
    private UserService userService;


    @GetMapping("/" + TOPLIST)
    @Secured("ROLE_USER")
    public String getToplistForUser(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute("users", userService.getAllNotBannedOrderByPoint(true));

        return TOPLIST;
    }
}
