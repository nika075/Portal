package com.portal.ludzie.service;

import com.portal.ludzie.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserService {
    User findUserByEmail(String email);

    void saveUser(User user);

    List<User> listAll(String keyword, int id);

    Page<User> findAll(Pageable pageable, int id);

    User findUserById(int id);

    void updateUserPassword(String newPassword, String email);

    void updateUserProfile(String newName, String newLastName, String newEmail, String hobby, int id);
}