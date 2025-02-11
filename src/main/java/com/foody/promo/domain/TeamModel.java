package com.foody.promo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TeamModel {
    private Long id;
    @NotNull
    @NotBlank
    @Size(max = 500)
    private String name;

    private String flagLink;
}
