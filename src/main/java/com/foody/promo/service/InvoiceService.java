package com.foody.promo.service;

import com.foody.promo.domain.InvoiceModel;
import com.foody.promo.entity.InvoiceEntity;
import com.foody.promo.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.foody.promo.utils.Utils.getUserId;
import static java.time.ZoneOffset.UTC;

@Service
public class InvoiceService {

    public static final int CODE_UPLOAD_LIMIT = 5;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private CodeUploadService codeUploadService;
    @Autowired
    private ConversionService conversionService;

    public List<InvoiceModel> getAllByUserId(Long id) {
        return invoiceRepository.findAllByUserId(id)
                .stream()
                .map(entity -> conversionService.convert(entity, InvoiceModel.class))
                .collect(Collectors.toList());
    }

    public Optional<InvoiceModel> getByApCodeAndBlockCode(String apCode, String blockCode) {
        return invoiceRepository.findByApCodeAndBlockCode(apCode, blockCode)
                .map(entity -> conversionService.convert(entity, InvoiceModel.class));
    }

    @Transactional
    public void saveInvoice(InvoiceModel model) {
        model.setPoint(codeUploadService.getActualCodeUploadValue());
        invoiceRepository.save(conversionService.convert(model, InvoiceEntity.class));

        userService.addPointAfterInvoiceUpload(model);
    }

    public boolean checkUploadDisabledForToday() {
        List<InvoiceEntity> uploads = invoiceRepository.findAllByUserId(getUserId());
        if (uploads.isEmpty()) {
            return false;
        }
        OffsetDateTime today = Instant.now().atOffset(UTC);

        long count = uploads.stream()
                .map(e -> Instant.ofEpochMilli(e.getUploadDate()).atOffset(UTC))
                .filter(a -> today.getDayOfYear() == a.getDayOfYear() && today.getYear() == today.getYear())
                .count();

        return count >= CODE_UPLOAD_LIMIT;
    }
}
