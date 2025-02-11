package com.foody.promo.domain;

import lombok.*;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointAndRankingModel {
    private Long point;
    private Long rank;
}
