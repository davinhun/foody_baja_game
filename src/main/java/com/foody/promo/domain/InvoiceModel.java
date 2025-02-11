package com.foody.promo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    @Null
    private Long id;
    @Null
    private Long userId;
    @NotBlank
    @NotNull
    @Size(max = 50)
    private String apCode;
    @NotBlank
    @NotNull
    @Size(max = 50)
    private String blockCode;
    @NotNull
    private Long blockDate;
    @Null
    private Long uploadDate;
    @Null
    private Long point;
}
