package com.learning.UserServiceFinal.Services;

import com.learning.UserServiceFinal.DTOs.UserDTO;
import com.learning.UserServiceFinal.Exceptions.NotFoundException;
import com.learning.UserServiceFinal.Models.Role;
import com.learning.UserServiceFinal.Models.User;
import com.learning.UserServiceFinal.Repositories.RoleRepository;
import com.learning.UserServiceFinal.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
    }

    public UserDTO getUserDetailsById(Long id) throws NotFoundException {
        Optional<User> userOptional= userRepository.findById(id);

        if(userOptional.isEmpty()){
            throw new NotFoundException("User not found!");
        }

        User user = userOptional.get();
        return UserDTO.from(user);
    }

    public UserDTO setUserRoles(Long userId, List<Long>roleIds) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new NotFoundException("User not found!");
        }
        User user = optionalUser.get();
        List<Role> roles = roleRepository.findAllById(roleIds);
        user.setRoles(Set.copyOf(roles));
        User saved = userRepository.save(user);
        return UserDTO.from(saved);
    }
}
