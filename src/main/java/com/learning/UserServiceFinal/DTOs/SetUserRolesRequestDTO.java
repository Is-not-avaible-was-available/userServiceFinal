package com.learning.UserServiceFinal.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Getter
@Setter
public class SetUserRolesRequestDTO {
    private List<Long> roleIds;
}
