package com.learning.UserServiceFinal.Services;

import com.learning.UserServiceFinal.DTOs.RoleDTO;
import com.learning.UserServiceFinal.Models.Role;
import com.learning.UserServiceFinal.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDTO createRole(String name){
        Role role = new Role();
        role.setName(name);

        Role saved= roleRepository.save(role);

        return RoleDTO.from(role);
    }
}
