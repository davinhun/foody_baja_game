package com.foody.promo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Set;


@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserModel {
    @Null
    private Long id;
    @Null
    private Long oauthId;
    @NotNull
    @NotBlank
    @Length(max = 500)
    private String name;
    @NotNull
    @NotBlank
    @Length(max = 500)
    private String email;
    @Null
    private Long availablePoints;
    @Null
    private String lastEvent;
    @Null
    private Long lastChange;
    @NotNull
    @NotBlank
    @Length(max = 500)
    private String password;
    @Null
    private Set<String> roles;
    private Boolean emailConfirmed;
    private Boolean banned = false;

    public void hideFields(boolean forUser) {
        setPassword("");
        if (forUser) {
            setEmail("");
            setId(null);
            setOauthId(null);
        }
    }
}
