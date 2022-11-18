package com.portal.ludzie.service;

import com.portal.ludzie.model.BlockedUser;

public interface BlockedUserService {
    void saveBlockedUser(BlockedUser blockedUser);

    BlockedUser findBlockedUserByusersIdAndUsersBlockedId(int id, int usr);
}
