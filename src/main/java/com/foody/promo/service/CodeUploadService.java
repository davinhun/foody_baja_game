package com.foody.promo.service;

import com.foody.promo.domain.CodeUploadModel;
import com.foody.promo.entity.CodeUploadEntity;
import com.foody.promo.repository.CodeUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.foody.promo.config.constants.Parameters.DEFAULT_POINT_VALUE;
import static com.foody.promo.config.constants.Parameters.ONLY_CODE_UPLOAD_ID;

@Service
public class CodeUploadService {

    @Autowired
    private CodeUploadRepository codeUploadRepository;
    @Autowired
    private ConversionService conversionService;

    public CodeUploadModel getActualCodeUpload() {
        return codeUploadRepository.findById(ONLY_CODE_UPLOAD_ID)
                .map(entity -> conversionService.convert(entity, CodeUploadModel.class))
                .orElse(null);
    }

    public Long getActualCodeUploadValue() {
        Long now = System.currentTimeMillis();
        return codeUploadRepository
                .findByIdAndCodeStartDateLessThanEqualAndCodeEndDateGreaterThanEqual(ONLY_CODE_UPLOAD_ID, now, now)
                .map(entity -> conversionService.convert(entity, CodeUploadModel.class).getCodeValue())
                .orElse(DEFAULT_POINT_VALUE);

    }

    @Transactional
    public void save(CodeUploadModel model) {
        codeUploadRepository.save(conversionService.convert(model, CodeUploadEntity.class));
    }
}
