package com.foody.promo.controller;

import com.foody.promo.domain.BetModel;
import com.foody.promo.service.BetService;
import com.foody.promo.service.MatchService;
import com.foody.promo.service.UserService;
import com.foody.promo.validator.BetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

import static com.foody.promo.config.constants.MatchStatus.*;
import static com.foody.promo.config.constants.Parameters.MATCHES_WITH_BETS;
import static com.foody.promo.config.constants.Parameters.POINT_AND_RANK;
import static com.foody.promo.config.path.PathConfig.BET;
import static com.foody.promo.config.path.PathConfig.RESULT;
import static com.foody.promo.utils.Utils.getUserId;

@Controller
public class BetController {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserService userService;
    @Autowired
    private BetService betService;
    @Autowired
    private BetValidator betValidator;

    @GetMapping("/" + BET)
    @Secured("ROLE_USER")
    public String getBets(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute(MATCHES_WITH_BETS, matchService.getWithBets(List.of(AVAILABLE, IN_PROGRESS), getUserId()));

        return BET;
    }

    @GetMapping("/" + RESULT)
    @Secured("ROLE_USER")
    public String getResults(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute(MATCHES_WITH_BETS, matchService.getAllFinishedOrBetted(List.of(AVAILABLE, IN_PROGRESS, FINISHED), getUserId()));

        return RESULT;
    }

    @PostMapping(value = "/" + BET)
    @Secured("ROLE_USER")
    public String postBet(@Valid @ModelAttribute BetModel betModel) {
        betValidator.validate(betModel);

        betService.saveMyBet(betModel);

        return "redirect:" + "/" + BET;
    }
}
