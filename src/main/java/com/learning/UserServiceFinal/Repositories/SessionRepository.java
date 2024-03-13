package com.learning.UserServiceFinal.Repositories;

import com.learning.UserServiceFinal.Models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findSessionByTokenAndUser_Id(String token, Long id);
}
