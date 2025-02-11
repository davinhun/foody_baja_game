package com.foody.promo.converter.invoice;

import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.entity.InvoiceEntity;
import org.springframework.core.convert.converter.Converter;

import static com.foody.promo.utils.Utils.getUserId;

public class InvoiceModelToInvoiceEntityConverter implements Converter<InvoiceModel, InvoiceEntity> {

    @Override
    public InvoiceEntity convert(InvoiceModel model) {
        return InvoiceEntity.builder()
                .id(model.getId())
                .userId(getUserId())
                .apCode(model.getApCode())
                .blockCode(model.getBlockCode())
                .blockDate(model.getBlockDate())
                .uploadDate(System.currentTimeMillis())
                .point(model.getPoint())
                .build();
    }
}
