package com.foody.promo.validator;

import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.service.InvoiceService;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.util.Optional;


@Component
public class InvoiceValidator {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserService userService;

    public void validate(InvoiceModel invoiceModel) {

        if (invoiceService.checkUploadDisabledForToday()) {
            throw new ValidationException("You already uploaded the max number today");
        }

        if (invoiceModel.getBlockDate() > System.currentTimeMillis()) {
            throw new ValidationException("Block code cannot after today");
        }

        Optional<InvoiceModel> model = invoiceService.getByApCodeAndBlockCode(invoiceModel.getApCode(), invoiceModel.getBlockCode());

        if (model.isPresent()) {
            throw new ValidationException("This AP code and block code already uploaded");
        }

    }
}

