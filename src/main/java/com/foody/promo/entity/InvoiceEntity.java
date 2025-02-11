package com.foody.promo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Invoice")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Audited
public class InvoiceEntity extends AbstractAuditedEntity {
    private Long userId;
    private String apCode;
    private String blockCode;
    private Long blockDate;
    private Long uploadDate;
    private Long point;
}
