package com.portal.ludzie.controller;

import com.portal.ludzie.model.User;
import com.portal.ludzie.service.UserService;
import com.portal.ludzie.utils.UserUtilities;
import com.portal.ludzie.validation.ChangePasswordValidator;
import com.portal.ludzie.validation.EditProfilValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfilController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/profil"}, method = RequestMethod.GET)
    public ModelAndView profilpage() {
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addObject("userName", user.getName());
        model.addObject("lastName", user.getLastName());
        model.addObject("email", user.getEmail());
        model.addObject("hobby", user.getHobby());
        model.setViewName("user/profil");
        return model;
    }

    @RequestMapping(value = "/editPassword", method = RequestMethod.GET)
    public ModelAndView editUserPassword() {
        ModelAndView m = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user = userService.findUserByEmail(username);
        m.addObject("user", user);
        m.setViewName("user/editPassword");
        return m;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ModelAndView changeUserPassword(User user, BindingResult result) {
        ModelAndView mod = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User us = userService.findUserByEmail(username);
        new ChangePasswordValidator().validate(user, result);
        new ChangePasswordValidator().checkPassword(user.getNewPassword(), result);
        if (result.hasErrors()) {
            mod.setViewName("user/editPassword");
        } else {
            userService.updateUserPassword(user.getNewPassword(), us.getEmail());
            mod.setViewName("help/afterEdit");
        }

        return mod;
    }

    @RequestMapping(value = "/editProfil", method = RequestMethod.GET)
    public ModelAndView changeUserData() {
        ModelAndView m = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user = userService.findUserByEmail(username);
        m.addObject("userEdit", user);
        m.setViewName("user/editProfil");
        return m;
    }

    @RequestMapping(value = "/updateProfil", method = RequestMethod.POST)
    public ModelAndView changeUserDataAction(User user, BindingResult result) {
        ModelAndView mod = new ModelAndView();
        new EditProfilValidator().validate(user, result);
        if (result.hasErrors()) {
            mod.setViewName("user/editProfil");
        } else {
            userService.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), user.getHobby(), user.getId());
            mod.setViewName("help/afterEdit");
        }
        return mod;
    }
}
