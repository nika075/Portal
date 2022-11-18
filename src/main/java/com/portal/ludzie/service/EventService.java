package com.portal.ludzie.service;

import com.portal.ludzie.model.Category;
import com.portal.ludzie.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

public interface EventService {
    List<Event> findByName(String name);

    Event findEventById(int id);

    List<Category> findAllCategory();

    void saveEvent(Event event);

    void deleteEventById(int id);

    List<Event> findAllEvent();

    Page<Event> findAll(Pageable pageable);

    Page<Event> findAllList(Pageable pageable, int id);

    List<Event> findAll();

    Page<Event> SearchEventByIdEvent(int id, Pageable pageable);

    List<Event> listAll(String keyword, int id);

    List<Event> listAllMy(String keyword, int id);

    List<Event> listAllByDate(Date date, int id);

    List<Event> listAllMyByDate(Date date, int id);

    void updateEvent(String newName, String place, Date newData, String newDescription, String newCost, Boolean newGroup, int id);

}