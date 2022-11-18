package com.portal.ludzie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.portal.ludzie.model.Event;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository("eventRepository")
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventById(int id);

    List<Event> findByName(String name);

    @Query(value = "Select p FROM Event p JOIN Category c ON p.category=c.id WHERE p.date >= current_date AND p.author_id != :id")
    Page<Event> allList(Pageable pageable, @Param("id") int Id);

    @Query(value = "SELECT * FROM Events p JOIN Category c ON p.events_category_id=c.category_id WHERE (CONCAT(p.events_name,p.events_place, p.events_category_id) " +
            "ILIKE %:keyword% OR c.category_name ILIKE %:keyword%) AND p.events_date >= current_date AND p.events_author_id != :id", nativeQuery = true)
    List<Event> search(@Param("keyword") String keyword, @Param("id") int id);

    @Query(value = "SELECT * FROM Events p JOIN Category c ON p.events_category_id=c.category_id WHERE (CONCAT(p.events_name,p.events_place, p.events_category_id) " +
            "ILIKE %:keyword% OR c.category_name ILIKE %:keyword%) AND p.events_date >= current_date AND p.events_author_id = :id", nativeQuery = true)
    List<Event> searchMy(@Param("keyword") String keyword, @Param("id") int id);

    @Query(value = "SELECT p FROM Event p JOIN Category c ON p.category=c.id WHERE CAST(p.date as date) = CAST(:date as date) AND p.author_id != :id")
    List<Event> searchDate(@Param("date") Date date, @Param("id") int id);

    @Query(value = "SELECT p FROM Event p JOIN Category c ON p.category=c.id WHERE CAST(p.date as date) = CAST(:date as date) AND p.author_id = :id")
    List<Event> searchMyDate(@Param("date") Date date, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.name = :newName, e.place = :newPlace, e.date= :newDate, e.description = :newDescription, e.cost = :newCost, e.group=:newGroup" +
            " WHERE e.id= :id")
    void updateEvent(@Param("newName") String newName, @Param("newPlace") String newPlace, @Param("newDate") Date newDate, @Param("newDescription") String newDescription,
                     @Param("newCost") String newCost, @Param("newGroup") Boolean newGroup, @Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM events WHERE events_id = :id", nativeQuery = true)
    void deleteEventFromEvents(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_user WHERE event_id = :id", nativeQuery = true)
    void deleteEventFromEventUser(@Param("id") int id);
}