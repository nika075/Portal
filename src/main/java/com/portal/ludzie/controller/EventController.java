package com.portal.ludzie.controller;

import com.portal.ludzie.model.Event;
import com.portal.ludzie.model.User;
import com.portal.ludzie.repository.BlockedRepository;
import com.portal.ludzie.repository.EventRepository;
import com.portal.ludzie.service.BlockedUserService;
import com.portal.ludzie.service.EventService;
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

import java.util.*;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private BlockedUserService blockedUserService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private BlockedRepository blockedRepository;
    private static int ELEMENTS = 5;

    @RequestMapping(value = {"/eventList/{page}"}, method = RequestMethod.GET)
    public ModelAndView eventPage(@PathVariable("page") int page) {
        ModelAndView model = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        model.addObject("roleMap", roleMap);
        String rol = roleMap.get(rola);
        if (rol == "admin") {
            username = "";
        }
        Page<Event> pages = getAllEventPageablee(page - 1, false, null, user1.getId());
        int totalPages = pages.getTotalPages();
        int currentPage = pages.getNumber();
        List<Event> eventList = pages.getContent();
        model.addObject("events", eventList);
        model.addObject("totalPages", totalPages);
        model.addObject("currentPage", currentPage + 1);
        model.addObject("eventList", eventList);
        model.addObject("recordStartCounter", currentPage + ELEMENTS);
        model.addObject("user", user1);
        model.addObject("rol", rol);
        model.setViewName("events/eventList");
        return model;
    }

    @RequestMapping(value = {"/eventSearch"}, method = RequestMethod.GET)
    public ModelAndView eventSearch(Model model, String keyword) {
        ModelAndView model2 = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        model2.addObject("roleMap", roleMap);
        String rol = roleMap.get(rola);
        if (rol == "admin") {
            username = "";
        }
        List<Event> listEvents = eventService.listAll(keyword, user1.getId());
        model2.addObject("user", user1);
        model2.addObject("rol", rol);
        model2.addObject("listEvents", listEvents);
        model2.addObject("keyword", keyword);
        model2.setViewName("events/eventSearch");
        return model2;
    }

    @RequestMapping(value = {"/eventMySearch"}, method = RequestMethod.GET)
    public ModelAndView eventSearchMyEvent(Model model, String keyword) {
        ModelAndView model2 = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        model2.addObject("roleMap", roleMap);
        String rol = roleMap.get(rola);
        List<Event> listEvents = eventService.listAllMy(keyword, user1.getId());
        model2.addObject("user", user1);
        model2.addObject("rol", rol);
        model2.addObject("listEvents", listEvents);
        model2.addObject("keyword", keyword);
        model2.setViewName("events/eventMySearch");
        return model2;
    }

    @RequestMapping(value = {"/eventSearchByDate"}, method = RequestMethod.GET)
    public ModelAndView eventByDate(Model model, Date date) {
        ModelAndView model2 = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        List<Event> listEvents = eventService.listAllByDate(date, user1.getId());
        model2.addObject("user", user1);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        String rol = roleMap.get(rola);
        model2.addObject("roleMap", roleMap);
        model2.addObject("rol", rol);
        model2.addObject("listEvents", listEvents);
        model2.addObject("date", date);
        model2.setViewName("events/eventSearchByDate");
        return model2;
    }

    @RequestMapping(value = {"/eventSearchMyByDate"}, method = RequestMethod.GET)
    public ModelAndView eventMyByDate(Model model, Date date) {
        ModelAndView model2 = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        List<Event> listEvents = eventService.listAllMyByDate(date, user1.getId());
        model2.addObject("user", user1);
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        String rol = roleMap.get(rola);
        model2.addObject("roleMap", roleMap);
        model2.addObject("rol", rol);
        model2.addObject("listEvents", listEvents);
        model2.addObject("date", date);
        model2.setViewName("events/eventSearchMyByDate");
        return model2;
    }

    @RequestMapping(value = "/eventList/edit/{id}", method = RequestMethod.GET)
    public ModelAndView getEventToEdit(@PathVariable("id") int id, Model model) {
        ModelAndView modelEdit = new ModelAndView();
        Event event = eventService.findEventById(id);
        modelEdit.addObject("eventEdit", event);
        modelEdit.setViewName("events/eventEdit");
        return modelEdit;
    }

    @RequestMapping(value = "/eventUpdate", method = RequestMethod.POST)
    public ModelAndView changeEventData(Event event, Model model) {
        ModelAndView mod = new ModelAndView();
        eventService.updateEvent(event.getName(), event.getPlace(), event.getDate(), event.getDescription(), event.getCost(), event.getGroup(), event.getId());
        mod.setViewName("help/afterEdit");
        return mod;
    }

    @RequestMapping(value = "/eventDelete/{id}", method = RequestMethod.GET)
    public ModelAndView eventDelete(@PathVariable("id") int id, Model model) {
        ModelAndView mod = new ModelAndView();
        eventService.deleteEventById(id);
        mod.addObject("events", eventRepository.findAll());
        mod.setViewName("help/afterDelete");
        return mod;
    }

    //zapis
    @RequestMapping(value = "/registerEvent/{id}", method = RequestMethod.GET)
    public ModelAndView eventRegistartion(@PathVariable("id") int id, Model model) {
        ModelAndView mod = new ModelAndView();
        Event event = eventService.findEventById(id);
        String username = UserUtilities.getLoggedUsername();
        User user = userService.findUserByEmail(username);
        if (blockedRepository.find(user.getId(), event.getAuthor_id()).size() == 0) {
            List<Event> eventList = eventService.findAll();
            event.getUserList().add(user);
            eventService.saveEvent(event);
            mod.addObject("eventList", eventList);
            mod.setViewName("help/afterRegister");
        } else mod.setViewName("help/failedRegister");

        return mod;
    }

    //uczestnicy
    @RequestMapping(value = "/userEvent/{id}", method = RequestMethod.GET)
    public ModelAndView userEvents(@PathVariable("id") int id, Model model) {
        ModelAndView mod = new ModelAndView();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        Event event = eventService.findEventById(id);
        Set<User> userList = event.getUserList();
        int ileuczest = userList.size();
        boolean pusta = userList.isEmpty();
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap = prepareRoleMap();
        int rola = user1.getRoles().iterator().next().getId();
        user1.setNrRoli(rola);
        String rol = roleMap.get(rola);
        mod.addObject("userList", userList);
        mod.addObject("ileuczest", ileuczest);
        mod.addObject("pusta", pusta);
        mod.addObject("rol", rol);
        mod.setViewName("events/userEvents");

        return mod;
    }

    //wydarzenia dodane przez danego użytkownika
    @RequestMapping(value = "/eventByUser", method = RequestMethod.GET)
    public ModelAndView eventByUser(Model model) {
        ModelAndView mod = new ModelAndView();
        List<Event> eventList = eventService.findAll();
        String username = UserUtilities.getLoggedUsername();
        User user1 = userService.findUserByEmail(username);
        List<Event> events = new ArrayList<>();
        eventList.forEach((c) -> {
            if (user1.getId() == c.getAuthor_id()) {
                events.add(c);
            }
        });
        mod.addObject("eventList", events);
        mod.setViewName("events/eventByUser");
        return mod;
    }

    private Page<Event> getAllEventPageablee(int page, boolean search, String param, int id) {
        Page<Event> pages;
        pages = eventService.findAllList(PageRequest.of(page, ELEMENTS, Sort.by("date")), id);

        return pages;
    }

    public Map<Integer, String> prepareRoleMap() {
        Map<Integer, String> roleMap = new HashMap<Integer, String>();
        roleMap.put(1, "admin");
        roleMap.put(2, "user");
        return roleMap;
    }
}
