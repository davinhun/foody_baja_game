package com.foody.promo.config.auth;

import com.foody.promo.domain.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@AllArgsConstructor
public class OauthUserDetails implements OAuth2User {

    private OAuth2User oAuth2User;
    @Getter
    private UserModel userModel;

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userModel.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return getAttributes().get("name").toString();
    }

    public Long getUserId() {
        return userModel.getId();
    }
}



