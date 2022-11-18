package com.portal.ludzie.repository;

import com.portal.ludzie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("adminRepository")
@Transactional
public interface AdminRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(value = "DELETE FROM users WHERE USERS_ID = :id", nativeQuery = true)
    void deleteUserFromUser(@Param("id") int id);

    @Modifying
    @Query(value = "DELETE FROM USER_ROLE WHERE USER_ID = :id", nativeQuery = true)
    void deleteUserFromUserRole(@Param("id") int id);

    @Modifying
    @Query(value = "DELETE FROM EVENT_USER WHERE USERS_ID = :id", nativeQuery = true)
    void deleteUserFromEventUser(@Param("id") int id);


    @Modifying
    @Query(value = "DELETE FROM EVENT_BLOCKED_USER WHERE USERS_BLOCKED_ID = :id OR USERS_ID = :id", nativeQuery = true)
    void deleteUserFromEvBlockUser(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE USER_ROLE  SET role_id = :roleId WHERE user_id= :id", nativeQuery = true)
    void updateRoleUser(@Param("roleId") int nrRoli, @Param("id") int id);
}
