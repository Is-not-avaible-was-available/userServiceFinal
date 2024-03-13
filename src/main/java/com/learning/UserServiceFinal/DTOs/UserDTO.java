package com.learning.UserServiceFinal.DTOs;

import com.learning.UserServiceFinal.Models.Role;
import com.learning.UserServiceFinal.Models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String name;
    private Set<Role> roles = new HashSet<>();


    public static UserDTO from(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        if(user.getRoles()!=null){
            userDTO.setRoles(user.getRoles());
        }
        return userDTO;
    }
}
