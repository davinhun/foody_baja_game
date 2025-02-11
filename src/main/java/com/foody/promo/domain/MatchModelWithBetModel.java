package com.foody.promo.domain;

import lombok.*;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchModelWithBetModel {
    private MatchModel match;
    private BetModel bet;
}
