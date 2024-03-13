package com.learning.UserServiceFinal.DTOs;

import com.learning.UserServiceFinal.Models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    private String name;


    public static RoleDTO from(Role role){
        RoleDTO roleDTO= new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }
}
