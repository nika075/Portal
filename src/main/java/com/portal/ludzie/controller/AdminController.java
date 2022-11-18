package com.portal.ludzie.controller;

import com.portal.ludzie.model.User;
import com.portal.ludzie.repository.UserRepository;
import com.portal.ludzie.service.AdminService;
import com.portal.ludzie.service.EventService;
import com.portal.ludzie.service.MessageService;
import com.portal.ludzie.service.UserService;
import com.portal.ludzie.utils.UserUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    private static int ELEMENTS = 5;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EventService eventService;
    @Autowired
    private MessageService emailSend;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"/userList/{page}"}, method = RequestMethod.GET)
    public ModelAndView userPage(@PathVariable("page") int page) {
        ModelAndView model = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        Page<User> pages = getAllUserPageable(page - 1, false, null, user1.getId());
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        List<User> userList = pages.getContent();
        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", currentPage + 1);
        model.addObject("userList", userList);
        model.addObject("recordStartCounter", currentPage + ELEMENTS);
        model.addObject("user", user1);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        String rol = roleMap.get(rola);
        model.addObject("rol", rol);
        model.setViewName("administrator/userList");
        return model;
    }

    @RequestMapping(value = {"/userSearch"}, method = RequestMethod.GET)
    public ModelAndView viewSearchPage(Model model, String keyword) {
        ModelAndView model2 = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        List<User> listUser = userService.listAll(keyword, user1.getId());
        model2.addObject("listUser", listUser);
        model2.addObject("keyword", keyword);
        model2.addObject("user", user1);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        model2.addObject("roleMap", roleMap);
        String rol = roleMap.get(rola);
        model2.addObject("rol", rol);
        model2.setViewName("administrator/userSearch");
        return model2;
    }

    @RequestMapping(value = "/userList/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getUserToEdit(@PathVariable("id") int id, Model model) {
        ModelAndView modelEdit = new ModelAndView();
        User user = new User();
        user = userService.findUserById(id);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user.getRoles().iterator().next().getId();
        user.setNrRoli(rola);
        model.addAttribute("roleMap", roleMap);
        model.addAttribute("userEdit", user);
        modelEdit.setViewName("administrator/userEdit");
        return modelEdit;
    }

    @RequestMapping(value = "/userUpdate/{id}", method = RequestMethod.POST)
    public ModelAndView changeUserDataAction(@PathVariable("id") int id, User user, Model model) {
        ModelAndView mod = new ModelAndView();
        int nrRoli = user.getNrRoli();
        adminService.updateUser(id, nrRoli);
        userService.updateUserProfile(user.getName(), user.getLastName(), user.getEmail(), user.getHobby(), user.getId());
        mod.setViewName("help/afterEdit");
        return mod;
    }

    @RequestMapping(value = "/userDelete/{id}", method = RequestMethod.GET)
    public ModelAndView userDelete(@PathVariable("id") int id, Model model) {
        ModelAndView mod = new ModelAndView();
        mod.addObject("userFs", userRepository.findAll());
        adminService.deleteUserById(id);
        mod.setViewName("help/afterDelete");
        return mod;
    }

    public Map<Integer, String> prepareRoleMap() {
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(1, "admin");
        roleMap.put(2, "user");
        return roleMap;
    }

    private Page<User> getAllUserPageable(int page, boolean search, String paramFF, int id) {
        Page<User> pages;
        if (!search) {
            pages = userService.findAll(PageRequest.of(page, ELEMENTS), id);
        } else {
            pages = userService.findAll(PageRequest.of(page, ELEMENTS, Sort.by("USERS_LAST_NAME")), id);
        }
        return pages;
    }
}