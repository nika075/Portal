package com.portal.ludzie.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USERS_ID")
    private int id;

    @Column(name = "USERS_EMAIL")
    @NotNull
    private String email;

    @Column(name = "USERS_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "USERS_NAME")
    @NotNull
    private String name;

    @Column(name = "USERS_LAST_NAME")
    @NotNull
    private String lastName;

    @Column(name = "USERS_ACTIVE")
    private int active;

    @Column(name = "USERS_HOBBY")
    private String hobby;

    @Column(name = "USERS_CONF_STATUS")
    private boolean confirmationStatus;

    @Column(name = "USERS_CONF_ID")
    private String confirmationId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles;

    @Transient
    private String newPassword;

    @Transient
    private int nrRoli;
}
