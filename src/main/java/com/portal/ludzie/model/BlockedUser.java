package com.portal.ludzie.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "EVENT_BLOCKED_USER")
@Getter
@Setter
public class BlockedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BLOCKED_ID")
    private int id;

    @Column(name = "USERS_BLOCKED_ID")
    private int usersBlockedId;

    @Column(name = "USERS_ID")
    private int usersId;
}
