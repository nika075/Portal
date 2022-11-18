package com.portal.ludzie.controller;

import javax.validation.Valid;

import com.portal.ludzie.model.Category;
import com.portal.ludzie.model.Event;
import com.portal.ludzie.repository.UserRepository;
import com.portal.ludzie.service.EventService;
import com.portal.ludzie.service.MessageService;
import com.portal.ludzie.utils.UserUtilities;
import com.portal.ludzie.validation.UserRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.portal.ludzie.model.User;
import com.portal.ludzie.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private MessageService emailSend;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("")
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("registration/login");
        return model;
    }

    @RequestMapping(value = {"/", "home/index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView model = new ModelAndView();

        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.setViewName("registration/signup");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        new UserRegisterValidator().validateEmailExist(userExists, bindingResult);
        new UserRegisterValidator().validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.setViewName("registration/signup");
        } else {
            user.setConfirmationId(createConfirmationID());
            emailSend.sendEmail("server.wydarzen@gmail.com", user.getEmail(), "Link do aktywacji", "http://localhost:8080/confirm?id=" + user.getConfirmationId());
            model.addObject("message", "Sprawdź swoją pocztę. Znajdź link aktywacyjny.");
            userService.saveUser(user);
            model.addObject("msg", "Użytkownik został zarejestrowany pomyślnie.");
            model.addObject("user", new User());
            model.setViewName("registration/succesRegistration");
        }
        return model;
    }

    @RequestMapping("/confirm")
    public ModelAndView confirm(@RequestParam(value = "id", required = true) String confirmationId, ModelAndView model) {
        User user = userRepository.getUserByConfirmationId(confirmationId);
        String message = "Nieprawidłowy identyfikator potwierdzający. Skontaktuj się z nami lub spróbuj ponownie.";
        if (user != null) {
            if (!user.isConfirmationStatus()) {
                user.setConfirmationStatus(true);
                user.setConfirmationId(null);
                userRepository.save(user);
            }
            message = user.getName() + ", twoje konto zostało zweryfikowane. Możesz się zalogować. ";
        }
        model.addObject("message", message);
        model.setViewName("home/index");
        return model;
    }

    @RequestMapping(value = "/home/home", method = RequestMethod.GET)
    public ModelAndView openMyEventPage(Model model) {
        ModelAndView mod = new ModelAndView();
        List<Event> eventList = eventService.findAll();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        List<Event> events = new ArrayList<>();
        Set<User> user;
        for (Event c : eventList) {
            user = c.getUserList();
            user.forEach((u) -> {
                        if (user1.getId() == u.getId()) {
                            events.add(c);
                        }
                    }
            );
        }
        mod.addObject("eventList", events);
        mod.setViewName("user/myEvent");
        return mod;
    }

    @RequestMapping(value = {"/addEvent"}, method = RequestMethod.GET)
    public ModelAndView addEvent() {
        ModelAndView model = new ModelAndView();
        Event event = new Event();
        List<Category> categories = eventService.findAllCategory();
        model.addObject("event", event);
        model.addObject("categories", categories);
        model.setViewName("user/addEvent");

        return model;
    }

    @RequestMapping(value = {"/addEvent"}, method = RequestMethod.POST)
    public ModelAndView createEvent(@Valid Event event, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        if (bindingResult.hasErrors()) {
            model.setViewName("user/addEvent");
        } else {
            eventService.saveEvent(event);
            model.addObject("msg", "Wydarzenie zostało dodane pomyślnie!");
            model.addObject("event", new Event());
            model.setViewName("help/afterAddEvent");
        }
        return model;
    }

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.setViewName("errors/access_denied");
        return model;
    }

    private String createConfirmationID() {
        return java.util.UUID.randomUUID().toString();
    }
}