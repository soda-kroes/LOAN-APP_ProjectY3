package com.java.rupp.loan_application.model;

import lombok.Data;

@Data
public  class LoanType {
    private int id;
    private String name;
    private double interestRate;
}