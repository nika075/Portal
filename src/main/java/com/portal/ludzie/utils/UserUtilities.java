package com.portal.ludzie.utils;

import com.portal.ludzie.model.User;
import com.portal.ludzie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtilities {
    @Autowired
    private UserRepository userRepository;

    public static String getLoggedUsername() {
        String username = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            username = auth.getName();
        }
        return username;
    }

    public User getLoggedUser() {
        String username = getLoggedUsername();
        if (username != null) {
            return userRepository.findByEmail(username);
        }
        return null;
    }
}
