package com.portal.ludzie.service;

public interface AdminService {
    void deleteUserById(int id);

    void updateUser(int id, int nrRoli);
}
