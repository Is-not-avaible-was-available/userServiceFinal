package com.learning.UserServiceFinal.Controllers;

import com.learning.UserServiceFinal.DTOs.SetUserRolesRequestDTO;
import com.learning.UserServiceFinal.DTOs.UserDTO;
import com.learning.UserServiceFinal.Exceptions.NotFoundException;
import com.learning.UserServiceFinal.Services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Long id) throws NotFoundException {
        UserDTO userDTO = userService.getUserDetailsById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDTO> setUserRoles(@PathVariable("id")Long id,
                             @RequestBody SetUserRolesRequestDTO setUserRolesRequestDTO) throws NotFoundException {
        UserDTO userDTO = userService.setUserRoles(id, setUserRolesRequestDTO.getRoleIds());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }
}
