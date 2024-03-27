package com.learning.UserServiceFinal.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDTO {
    private String to;
    private String from;
    private String subject;
    private String message;
}
