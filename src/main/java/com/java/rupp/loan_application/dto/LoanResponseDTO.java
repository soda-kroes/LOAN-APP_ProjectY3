package com.java.rupp.loan_application.dto;

import com.java.rupp.loan_application.model.Branch;
import com.java.rupp.loan_application.model.LoanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String maritalStatus;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String nationalityId;
    private String nationalityImage;
    private String selfieImage;
    private String address;
    private double loanAmount;
    private double monthlyPayment;
    private int loanTerm;
    private String currency;
    private double totalOfPayment;
    private double totalInterest;
    private double interestRate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String branchCode;
    private int loanTypeValue;
}

