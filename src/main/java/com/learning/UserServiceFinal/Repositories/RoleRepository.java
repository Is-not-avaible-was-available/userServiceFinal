package com.learning.UserServiceFinal.Repositories;

import com.learning.UserServiceFinal.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
