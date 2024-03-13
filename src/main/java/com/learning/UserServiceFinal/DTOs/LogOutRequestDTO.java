package com.learning.UserServiceFinal.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutRequestDTO {
    private String token;
    private Long userId;
}
