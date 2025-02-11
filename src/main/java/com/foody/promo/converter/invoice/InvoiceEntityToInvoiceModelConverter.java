package com.foody.promo.converter.invoice;

import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.entity.InvoiceEntity;
import org.springframework.core.convert.converter.Converter;

public class InvoiceEntityToInvoiceModelConverter implements Converter<InvoiceEntity, InvoiceModel> {

    @Override
    public InvoiceModel convert(InvoiceEntity entity) {
        return new InvoiceModel(entity.getId(), entity.getUserId(), entity.getApCode(),
                entity.getBlockCode(), entity.getBlockDate(), entity.getUploadDate(), entity.getPoint());

    }
}
