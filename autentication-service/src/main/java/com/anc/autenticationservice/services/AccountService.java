package com.anc.autenticationservice.services;

import com.anc.autenticationservice.entities.AppRole;
import com.anc.autenticationservice.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);

    AppRole addNewRole(AppRole appRole);

    void addRoleToUser(String username, String roleName);

    AppUser loadUserByUsername(String username);

    List<AppUser> listUsers();
}
