package com.foody.promo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "Match")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Audited
public class MatchEntity extends AbstractAuditedEntity {
    @OneToOne
    @JoinColumn(name = "team1", referencedColumnName = "id")
    private TeamEntity team1;
    @OneToOne
    @JoinColumn(name = "team2", referencedColumnName = "id")
    private TeamEntity team2;
    private Long startDate;
    private String result;
    private String numericResult;
    private Double oddsTeam1;
    private Double oddsDraw;
    private Double oddsTeam2;
    private String status;
    @Column(name = "match_group")
    private String group;
}
