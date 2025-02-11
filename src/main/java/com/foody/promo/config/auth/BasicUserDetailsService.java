package com.foody.promo.config.auth;


import com.foody.promo.domain.UserModel;
import com.foody.promo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BasicUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Optional<UserModel> userModel = userService.getByEmail(email);
        if (userModel.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        return new ExtendedUserDetails(userModel.get());
    }

}