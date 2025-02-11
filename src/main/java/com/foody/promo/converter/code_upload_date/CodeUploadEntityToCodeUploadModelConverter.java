package com.foody.promo.converter.code_upload_date;

import com.foody.promo.domain.CodeUploadModel;
import com.foody.promo.entity.CodeUploadEntity;
import org.springframework.core.convert.converter.Converter;

import static com.foody.promo.config.constants.Parameters.ONLY_CODE_UPLOAD_ID;

public class CodeUploadEntityToCodeUploadModelConverter implements Converter<CodeUploadEntity, CodeUploadModel> {

    @Override
    public CodeUploadModel convert(CodeUploadEntity entity) {
        return new CodeUploadModel(ONLY_CODE_UPLOAD_ID, entity.getCodeStartDate(), entity.getCodeValue(), entity.getCodeEndDate());
    }
}
