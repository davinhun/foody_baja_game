package com.foody.promo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MatchModel {
    private Long id;
    @NotNull
    private TeamModel team1;
    @NotNull
    private TeamModel team2;
    @NotNull
    private Long startDate;
    private String result;
    private String numericResult;
    @NotNull
    @Min(1)
    @Digits(integer = 3, fraction = 2)
    private Double oddsTeam1;
    @NotNull
    @Min(1)
    @Digits(integer = 3, fraction = 2)
    private Double oddsDraw;
    @NotNull
    @Min(1)
    @Digits(integer = 3, fraction = 2)
    private Double oddsTeam2;
    @NotEmpty
    private String status;
    @NotNull
    @NotBlank
    private String group;
}
