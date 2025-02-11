package com.foody.promo.controller;

import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.service.CodeUploadService;
import com.foody.promo.service.InvoiceService;
import com.foody.promo.service.UserService;
import com.foody.promo.validator.InvoiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.foody.promo.config.constants.Parameters.ACTUAL_CODE_UPLOAD_VALUE;
import static com.foody.promo.config.constants.Parameters.POINT_AND_RANK;
import static com.foody.promo.config.path.PathConfig.INVOICE;
import static com.foody.promo.utils.Utils.getUserId;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvoiceValidator validator;
    @Autowired
    private CodeUploadService codeUploadService;

    @GetMapping("/" + INVOICE)
    @Secured("ROLE_USER")
    public String getInvoices(Model model) {
        model.addAttribute(POINT_AND_RANK, userService.getRankingFor(getUserId()));
        model.addAttribute("invoices", invoiceService.getAllByUserId(getUserId()));
        model.addAttribute("uploadDisabled", invoiceService.checkUploadDisabledForToday());
        model.addAttribute(ACTUAL_CODE_UPLOAD_VALUE, codeUploadService.getActualCodeUploadValue());

        return INVOICE;
    }

    @PostMapping(value = "/" + INVOICE)
    @Secured("ROLE_USER")
    public String postInvoice(@Valid @ModelAttribute InvoiceModel invoiceModel) {
        validator.validate(invoiceModel);
        invoiceService.saveInvoice(invoiceModel);

        return "redirect:" + INVOICE;
    }
}
