package com.foody.promo.config.auth;

import com.foody.promo.domain.UserModel;
import com.foody.promo.entity.UserEntity;
import com.foody.promo.service.LoginRegisterService;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static com.foody.promo.config.auth.RoleTypes.ROLE_USER;
import static com.foody.promo.config.constants.EventTypes.REGISTERED_FROM_FB;
import static com.foody.promo.config.constants.Parameters.REGISTER_START_POINT;


@Component
public
class OauthHandlerService extends DefaultOAuth2UserService implements AuthenticationSuccessHandler {


    @Autowired
    private UserService userService;
    @Autowired
    private LoginRegisterService loginRegisterService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Long facebookId = Long.valueOf(oAuth2User.getAttribute("id"));
        String email = oAuth2User.getAttribute("email");
        Optional<UserModel> byOauthId = userService.getByOauthId(facebookId);

        UserModel model = getOrRegisterUser(byOauthId, facebookId, email, oAuth2User);
        return new OauthUserDetails(oAuth2User, model);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/");
    }

    private UserModel getOrRegisterUser(Optional<UserModel> byOauthId, Long facebookId, String email, OAuth2User oAuth2User) {
        if (byOauthId.isEmpty()) {
            if (email != null && userService.getByEmail(email).isPresent()) {
                throw new UsernameNotFoundException("Already registered with this email");
            }
            return registerUser(oAuth2User, facebookId, email);
        } else {
            return byOauthId.get();
        }
    }

    private UserModel registerUser(OAuth2User auth2User, Long facebookId, String email) {
        UserEntity entity = UserEntity.builder()
                .oauthId(facebookId)
                .name(auth2User.getAttribute("name"))
                .email(email)
                .availablePoints(REGISTER_START_POINT)
                .lastEvent(REGISTERED_FROM_FB)
                .lastChange(0L)
                .roles(Set.of(ROLE_USER))
                .emailConfirmed(true)
                .build();

        return loginRegisterService.registerOauthUser(entity);
    }

}
