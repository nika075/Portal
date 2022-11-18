package com.portal.ludzie.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EVENTS")
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENTS_ID")
    private int id;

    @Column(name = "EVENTS_NAME")
    @NotNull
    private String name;

    @Column(name = "EVENTS_PLACE")
    @NotNull
    private String place;

    @Column(name = "EVENTS_DESCRIPTION")
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "EVENTS_CATEGORY_ID")
    private Category category;

    @Column(name = "EVENTS_DATE")
    @NotNull
    private Date date;

    @Column(name = "EVENTS_COST")
    private String cost;

    @Column(name = "EVENTS_GROUP")
    private Boolean group;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "EVENT_USER",
            joinColumns = {@JoinColumn(name = "EVENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USERS_ID")}
    )
    Set<User> userList = new HashSet<>();

    @Column(name = "EVENTS_AUTHOR_ID")
    private int author_id;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    public Date getDate() {
        return date;
    }
}
