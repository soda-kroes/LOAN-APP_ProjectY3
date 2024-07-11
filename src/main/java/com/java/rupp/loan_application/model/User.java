package com.java.rupp.loan_application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String role;
}
