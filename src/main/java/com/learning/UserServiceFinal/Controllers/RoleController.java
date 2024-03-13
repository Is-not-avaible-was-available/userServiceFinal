package com.learning.UserServiceFinal.Controllers;

import com.learning.UserServiceFinal.DTOs.CreateRoleRequestDTO;
import com.learning.UserServiceFinal.DTOs.RoleDTO;
import com.learning.UserServiceFinal.Services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/roles")
@RestController
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody CreateRoleRequestDTO createRoleRequestDTO){
        RoleDTO roleDTO = roleService.createRole(createRoleRequestDTO.getName());

        return new ResponseEntity<>(roleDTO, HttpStatus.CREATED);
    }
}
