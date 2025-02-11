package com.foody.promo.controller;

import com.foody.promo.config.constants.MatchStatus;
import com.foody.promo.config.constants.ResultTypes;
import com.foody.promo.domain.CodeUploadModel;
import com.foody.promo.domain.MatchModel;
import com.foody.promo.domain.TeamModel;
import com.foody.promo.service.*;
import com.foody.promo.validator.MatchValidator;
import com.foody.promo.validator.TeamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

import static com.foody.promo.config.constants.MatchStatus.*;
import static com.foody.promo.config.constants.Parameters.*;
import static com.foody.promo.config.path.PathConfig.*;
import static com.foody.promo.utils.Utils.getUserId;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchValidator matchValidator;
    @Autowired
    private CodeUploadService codeUploadService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamValidator teamValidator;


    @GetMapping("/" + ADMIN + "/" + MATCH)
    @Secured("ROLE_ADMIN")
    public String getMatches(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute(MATCHES, adminService.getAll());
        model.addAttribute("matchStatus", MatchStatus.getAllMatchStatus());
        model.addAttribute("results", ResultTypes.getAllResultTypes());
        model.addAttribute(TEAMS, teamService.getAll());

        return ADMIN + "/" + MATCH;
    }

    @GetMapping("/" + ADMIN + "/" + CODE_UPLOAD)
    @Secured("ROLE_ADMIN")
    public String getCodeUploadValue(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute("codeUpload", codeUploadService.getActualCodeUpload());
        model.addAttribute(ACTUAL_CODE_UPLOAD_VALUE, codeUploadService.getActualCodeUploadValue());

        return ADMIN + "/" + CODE_UPLOAD;
    }

    @GetMapping("/" + ADMIN + "/" + TEAM)
    @Secured("ROLE_ADMIN")
    public String getTeams(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute(TEAMS, teamService.getAll());

        return ADMIN + "/" + TEAM;
    }

    @GetMapping("/" + ADMIN + "/" + TOPLIST)
    @Secured("ROLE_ADMIN")
    public String getToplistForAdmin(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute("users", userService.getAllOrderByPoint(false));

        return ADMIN + "/" + TOPLIST;
    }

    @GetMapping("/" + ADMIN + "/" + USER + "/" + "{id}")
    @Secured("ROLE_ADMIN")
    public String getOneUserBet(Model model, @PathVariable Long id) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute(MATCHES_WITH_BETS, matchService.getWithBets(List.of(AVAILABLE, IN_PROGRESS, FINISHED), id));

        return RESULT;
    }

    @GetMapping("/" + ADMIN + "/" + INVOICE + "/" + "{id}")
    @Secured("ROLE_ADMIN")
    public String getInvoicesForUser(Model model, @PathVariable Long id) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute("invoices", invoiceService.getAllByUserId(id));
        model.addAttribute("uploadDisabled", true);
        model.addAttribute(ACTUAL_CODE_UPLOAD_VALUE, codeUploadService.getActualCodeUploadValue());

        return INVOICE;
    }

    @PostMapping(value = "/" + ADMIN + "/" + MATCH)
    @Secured("ROLE_ADMIN")
    public String postMatch(@Valid @ModelAttribute MatchModel matchModel) {

        matchValidator.validate(matchModel);
        adminService.saveAndUpdateWinnings(matchModel);

        return "redirect:/" + ADMIN + "/" + MATCH;
    }

    @PostMapping(value = "/" + ADMIN + "/" + CODE_UPLOAD)
    @Secured("ROLE_ADMIN")
    public String postCodeUpload(@Valid @ModelAttribute CodeUploadModel codeUploadModel) {

        codeUploadService.save(codeUploadModel);

        return "redirect:/" + ADMIN + "/" + CODE_UPLOAD;
    }

    @PostMapping("/" + ADMIN + "/" + TEAM)
    @Secured("ROLE_ADMIN")
    public String postTeam(@Valid @ModelAttribute TeamModel model) {
        teamValidator.validate(model);
        teamService.save(model);

        return "redirect:/" + ADMIN + "/" + TEAM;
    }

    @PostMapping(value = "/" + ADMIN + "/" + BAN_USER)
    @Secured("ROLE_ADMIN")
    public String banUser(@Valid Long banUserId) {

        adminService.banUser(banUserId);

        return "redirect:/" + ADMIN + "/" + TOPLIST;
    }


}
