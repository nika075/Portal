package com.portal.ludzie.service;

import com.portal.ludzie.model.Role;
import com.portal.ludzie.model.User;
import com.portal.ludzie.repository.RoleRespository;
import com.portal.ludzie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRespository roleRespository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        //zmiana wartości w findByRole - "ADMIN" by stworzyć administratora, "USER" by stworzyć użytkownika
        Role userRole = roleRespository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        User user = userRepository.findUserById(id);
        return user;
    }

    @Override
    public Page<User> findAll(Pageable pageable, int id) {

        Page<User> userLis = userRepository.findAll(pageable);
        List<User> result = userLis.getContent()
                .stream()
                .filter(user -> user.getId() != id)
                .collect(Collectors.toList());
        Page<User> userList = new PageImpl<User>(result);

        return userList;
    }

    @Override
    public List<User> listAll(String keyword, int id) {
        if (keyword != null) {
            return userRepository.search(keyword, id);
        }
        return userRepository.findAll();
    }

    @Override
    public void updateUserPassword(String newPassword, String email) {
        userRepository.updateUserPassword(bCryptPasswordEncoder.encode(newPassword), email);
    }

    @Override
    public void updateUserProfile(String newName, String newLastName, String newEmail, String hobby, int id) {
        userRepository.updateUserProfile(newName, newLastName, newEmail, hobby, id);
    }
}