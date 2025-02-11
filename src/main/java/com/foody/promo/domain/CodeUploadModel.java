package com.foody.promo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CodeUploadModel {
    private Long id;
    @NotNull
    private Long codeStartDate;
    @NotNull
    @Min(1)
    @Max(5000)
    private Long codeValue;
    @NotNull
    private Long codeEndDate;


    @AssertTrue
    public boolean isValidDate() {
        if (codeStartDate == null || codeEndDate == null) {
            return true;
        }
        return codeStartDate < codeEndDate;
    }
}
