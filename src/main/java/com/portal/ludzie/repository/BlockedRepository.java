package com.portal.ludzie.repository;

import com.portal.ludzie.model.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("blockedRepository")
public interface BlockedRepository extends JpaRepository<BlockedUser, Integer> {
    @Query(value = "SELECT usersId FROM BlockedUser p WHERE usersBlockedId =:keyword AND usersId=:userId")
    List<Integer> find(@Param("keyword") Integer keyword, @Param("userId") Integer userId);

    BlockedUser findBlockedUserByusersIdAndUsersBlockedId(int id, int usr);
}
