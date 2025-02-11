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
public class BetModel {
    @Null
    private Long id;
    @NotNull
    private MatchModel match;
    @Null
    private UserModel user;
    @NotEmpty
    @Pattern(regexp = "team1|draw|team2")
    private String guess;
    @NotNull
    @Min(1)
    private Long point;

    @Null
    private Long winning;
    @Null
    private Long possibleWinning;
}
