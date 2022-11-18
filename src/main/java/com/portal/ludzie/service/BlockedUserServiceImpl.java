package com.portal.ludzie.service;

import com.portal.ludzie.model.BlockedUser;
import com.portal.ludzie.repository.BlockedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BlockedUserService")
public class BlockedUserServiceImpl implements BlockedUserService {
    @Autowired
    private BlockedRepository blockedRepository;

    @Override
    public void saveBlockedUser(BlockedUser blockedUser) {
        blockedRepository.save(blockedUser);
    }

    @Override
    public BlockedUser findBlockedUserByusersIdAndUsersBlockedId(int id, int usr) {
        BlockedUser event = blockedRepository.findBlockedUserByusersIdAndUsersBlockedId(id, usr);
        return event;
    }
}
