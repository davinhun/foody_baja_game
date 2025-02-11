package com.foody.promo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "Bet")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Audited
public class BetEntity extends AbstractAuditedEntity {
    @OneToOne
    @JoinColumn(name = "match", referencedColumnName = "id")
    private MatchEntity match;
    @OneToOne
    @JoinColumn(name = "appUser", referencedColumnName = "id")
    private UserEntity appUser;
    private String guess;
    private Long point;
    private Long winning;
    private Long possibleWinning;
}
