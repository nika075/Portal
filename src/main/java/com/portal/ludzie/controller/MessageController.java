package com.portal.ludzie.controller;

import com.portal.ludzie.model.Event;
import com.portal.ludzie.model.Mail;
import com.portal.ludzie.model.User;
import com.portal.ludzie.service.EventService;
import com.portal.ludzie.service.MessageService;
import com.portal.ludzie.service.UserService;
import com.portal.ludzie.utils.UserUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MessageService messageService;

    //mail do wszystkich uczestnikow
    @RequestMapping(value = "/event/send/{id}", method = RequestMethod.GET)
    public ModelAndView sendMailForAllEvent(@PathVariable("id") int id) {
        Mail mail = new Mail();
        ModelAndView model = new ModelAndView();
        model.addObject("mail", mail);
        Event event = eventService.findEventById(id);
        ArrayList<String> emailList = new ArrayList<>();
        event.getUserList().forEach((c) -> emailList.add(c.getEmail()));
        model.addObject("mailUser", emailList.toString().replace("[", "").replace("]", "").replace(" ", ""));
        model.setViewName("mail/sendMail");
        return model;
    }

    @RequestMapping(value = "/event/send/sendMail", method = RequestMethod.POST)
    public ModelAndView sendMail(Mail mail) {
        ModelAndView model = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        messageService.sendEmail(username, mail.getTo(), mail.getSubject(), mail.getText());
        model.setViewName("help/sendedMail");
        return model;
    }

    //mail do użytkownika
    @RequestMapping(value = "/send/{id}", method = RequestMethod.GET)
    public ModelAndView sendMailForUser(@PathVariable("id") int id) {
        Mail mail = new Mail();
        ModelAndView model = new ModelAndView();
        model.addObject("mail", mail);
        User user = userService.findUserById(id);
        model.addObject("mailUser", user.getEmail());
        model.setViewName("mail/sendMail");
        return model;
    }

    //mail do użytkownika bez maila
    @RequestMapping(value = "/sendMail/{id}", method = RequestMethod.GET)
    public ModelAndView sendMail(@PathVariable("id") int id) {
        Mail mail = new Mail();
        ModelAndView model = new ModelAndView();
        model.addObject("mail", mail);
        User user = userService.findUserById(id);
        model.addObject("mailUser", user.getEmail());
        model.setViewName("mail/send");
        return model;
    }

    @RequestMapping(value = "/mailToAuthor/{id}", method = RequestMethod.GET)
    public ModelAndView mailToAuthor(@PathVariable int id, Model model) {
        ModelAndView mod = new ModelAndView();
        Mail mail = new Mail();
        Event event = eventService.findEventById(id);
        mod.addObject("mail", mail);
        mod.addObject("mailUser", userService.findUserById(event.getAuthor_id()).getEmail());
        mod.setViewName("mail/send");
        return mod;
    }
}


