package com.learning.UserServiceFinal.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Entity
@Getter
@Setter
@JsonDeserialize(as = Setter.class)
public class Session extends BaseModel{

    private String token;
    @Enumerated(value = EnumType.ORDINAL)
    private SessionStatus sessionStatus;
    private Date expiryAt;
    @ManyToOne
    private User user;
}
