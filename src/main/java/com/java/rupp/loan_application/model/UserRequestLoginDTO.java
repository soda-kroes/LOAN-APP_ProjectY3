package com.java.rupp.loan_application.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRequestLoginDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
