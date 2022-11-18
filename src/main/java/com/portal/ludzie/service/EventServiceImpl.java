package com.portal.ludzie.service;

import com.portal.ludzie.model.Category;
import com.portal.ludzie.model.Event;
import com.portal.ludzie.repository.CategoryRepository;
import com.portal.ludzie.repository.EventRepository;
import com.portal.ludzie.utils.UserUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("eventService")
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserUtilities userUtilities;
    @Autowired
    private UserService userService;

    @Override
    public Event findEventById(int id) {
        Event event = eventRepository.findEventById(id);
        return event;
    }

    @Override
    public List<Event> findByName(String name) {
        List<Event> listEv = eventRepository.findByName(name);
        return listEv;
    }

    @Override
    public void updateEvent(String newName, String place, Date newDate, String newDescription, String newCost, Boolean newGroup, int id) {
        eventRepository.updateEvent(newName, place, newDate, newDescription, newCost, newGroup, id);
    }

    @Override
    public List<Category> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public List<Event> findAllEvent() {
        List<Event> events = eventRepository.findAll();
        return events;
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        Page<Event> eventList = eventRepository.findAll(pageable);
        return eventList;
    }

    @Override
    public Page<Event> findAllList(Pageable pageable, int id) {
        Page<Event> eventList = eventRepository.allList(pageable, id);
        return eventList;
    }

    @Override
    public List<Event> findAll() {
        List<Event> eventList = eventRepository.findAll();
        return eventList;
    }

    @Override
    public Page<Event> SearchEventByIdEvent(int id, Pageable pageable) {
        return null;
    }

    @Override
    public void saveEvent(Event event) {
        String username = UserUtilities.getLoggedUsername();
        if (event.getAuthor_id() == 0) {
            event.setAuthor_id(userService.findUserByEmail(username).getId());
        }
        eventRepository.save(event);
    }

    @Override
    public void deleteEventById(int id) {
        eventRepository.deleteEventFromEventUser(id);
        //eventRepository.deleteEventFromEventByUser(id);
        eventRepository.deleteEventFromEvents(id);

    }

    @Override
    public List<Event> listAll(String keyword, int id) {
        if (keyword != null) {
            return eventRepository.search(keyword, id);
        }
        return eventRepository.findAll();
    }

    @Override
    public List<Event> listAllMy(String keyword, int id) {
        if (keyword != null) {
            return eventRepository.searchMy(keyword, id);
        }
        return eventRepository.findAll();
    }

    @Override
    public List<Event> listAllByDate(Date date, int id) {
        if (date != null) {
            return eventRepository.searchDate(date, id);
        }
        return eventRepository.findAll();
    }

    @Override
    public List<Event> listAllMyByDate(Date date, int id) {
        if (date != null) {
            return eventRepository.searchMyDate(date, id);
        }
        return eventRepository.findAll();
    }
}