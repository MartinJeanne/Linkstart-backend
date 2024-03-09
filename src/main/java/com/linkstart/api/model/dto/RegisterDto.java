package com.linkstart.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    private String clientName;
    private String password;
    private String registerPassword;
}
