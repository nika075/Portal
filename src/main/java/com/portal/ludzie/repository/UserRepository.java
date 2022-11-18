package com.portal.ludzie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.portal.ludzie.model.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserById(int id);

    User getUserByConfirmationId(String id);

    @Query(value = "SELECT p FROM User p  WHERE CONCAT(p.lastName, p.name)  LIKE %:keyword% AND p.id != :id")
    List<User> search(@Param("keyword") String keyword, @Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email= :email")
    void updateUserPassword(@Param("newPassword") String password, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :newName, u.lastName = :newLastName, u.email = :newEmail, u.hobby = :newHobby" +
            " WHERE u.id= :id")
    void updateUserProfile(@Param("newName") String newName, @Param("newLastName") String newLastName,
                           @Param("newEmail") String newEmail, @Param("newHobby") String newHobby, @Param("id") Integer id);
}