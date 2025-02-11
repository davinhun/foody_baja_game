package com.foody.promo.converter.code_upload_date;

import com.foody.promo.domain.CodeUploadModel;
import com.foody.promo.entity.CodeUploadEntity;
import org.springframework.core.convert.converter.Converter;

import static com.foody.promo.config.constants.Parameters.ONLY_CODE_UPLOAD_ID;

public class CodeUploadModelToCodeUploadEntityConverter implements Converter<CodeUploadModel, CodeUploadEntity> {

    @Override
    public CodeUploadEntity convert(CodeUploadModel model) {
        return CodeUploadEntity.builder()
                .id(ONLY_CODE_UPLOAD_ID)
                .codeStartDate(model.getCodeStartDate())
                .codeValue(model.getCodeValue())
                .codeEndDate(model.getCodeEndDate())
                .build();
    }
}
