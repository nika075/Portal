package com.portal.ludzie.service;

import com.portal.ludzie.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void deleteUserById(int id) {
        adminRepository.deleteUserFromUserRole(id);
        adminRepository.deleteUserFromEventUser(id);
        adminRepository.deleteUserFromEvBlockUser(id);
        adminRepository.deleteUserFromUser(id);
    }

    @Override
    public void updateUser(int id, int nrRoli) {
        adminRepository.updateRoleUser(nrRoli, id);
    }
}
