package com.foody.promo.entity;

import com.foody.promo.converter.SetToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "app_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Audited
public class UserEntity extends AbstractAuditedEntity {
    private Long oauthId;
    private String name;
    private String email;
    private Long availablePoints;
    private String lastEvent;
    private Long lastChange;
    private String password;
    @Convert(converter = SetToStringConverter.class)
    private Set<String> roles;
    private Boolean emailConfirmed;
    private boolean banned;
}
