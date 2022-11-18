package com.portal.ludzie.controller;

import com.portal.ludzie.model.BlockedUser;
import com.portal.ludzie.model.User;
import com.portal.ludzie.service.BlockedUserService;
import com.portal.ludzie.service.UserService;
import com.portal.ludzie.utils.UserUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BlockedController {
    @Autowired
    private BlockedUserService blockedUserService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/blockUser/{id}"}, method = RequestMethod.GET)
    public ModelAndView blockUser(@PathVariable("id") int id) {
        ModelAndView model = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user = userService.findUserByEmail(username);
        BlockedUser u = new BlockedUser();
        u.setUsersId(user.getId());
        u.setUsersBlockedId(id);
        User blUs = userService.findUserById(id);
        model.addObject("blName", blUs.getName());
        model.addObject("blLastName", blUs.getLastName());
        model.addObject("blocked", u);
        model.setViewName("help/blockedUser");

        return model;
    }

    @RequestMapping(value = "/blockedUser", method = RequestMethod.POST)
    public ModelAndView blockedUser(BlockedUser blockedUser, Model model) {
        ModelAndView mod = new ModelAndView();
        blockedUserService.saveBlockedUser(blockedUser);
        mod.setViewName("help/afterBlocked");
        return mod;
    }
}
