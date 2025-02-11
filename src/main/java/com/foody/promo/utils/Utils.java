package com.foody.promo.utils;

import com.foody.promo.config.auth.ExtendedUserDetails;
import com.foody.promo.config.auth.OauthUserDetails;
import com.foody.promo.domain.UserModel;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.nimbusds.oauth2.sdk.util.StringUtils.isBlank;

public class Utils {

    public static Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OauthUserDetails) {
            OauthUserDetails oAuth2User = ((OauthUserDetails) principal);
            return oAuth2User.getUserId();
        }
        if (principal instanceof ExtendedUserDetails) {
            ExtendedUserDetails extendedUserDetails = ((ExtendedUserDetails) principal);
            return extendedUserDetails.getUserId();
        }
        throw new IllegalArgumentException("Cannot get UserId");
    }

    public static String getUserName() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return "first registration";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OauthUserDetails) {
            OauthUserDetails oAuth2User = ((OauthUserDetails) principal);
            return oAuth2User.getName();
        }
        if (principal instanceof ExtendedUserDetails) {
            ExtendedUserDetails extendedUserDetails = ((ExtendedUserDetails) principal);
            return extendedUserDetails.getUsername();
        }
        if (principal instanceof String) {
            String beforeReg = (String) principal;
            if (beforeReg.equals("anonymousUser")) {
                return "first registration";
            }
        }
        throw new IllegalArgumentException("Cannot get UserId");
    }

    public static UserModel getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof OauthUserDetails) {
            OauthUserDetails oAuth2User = ((OauthUserDetails) principal);
            return oAuth2User.getUserModel();
        }
        if (principal instanceof ExtendedUserDetails) {
            ExtendedUserDetails extendedUserDetails = ((ExtendedUserDetails) principal);
            return extendedUserDetails.getUserModel();
        }
        throw new IllegalArgumentException("Cannot get UserId");
    }

    public static String convertEmptyToNull(String input) {
        return isBlank(input) ? null : input;
    }
}
