package com.java.rupp.loan_application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LoanRequestDTO {

    @JsonProperty("request_no")
    private Long requestNo;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("marital_status")
    private String maritalStatus;

    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("email")
    private String email;

    @JsonProperty("nationality_id")
    private String nationalityId;

    @JsonProperty("nationality_image")
    private String nationalityImage;

    @JsonProperty("selfie_image")
    private String selfieImage;

    @JsonProperty("address")
    private String address;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("loan_type_id")
    private Long loanTypeId;

    @JsonProperty("loan_term")
    private Integer loanTerm;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("monthly_payment")
    private BigDecimal monthlyPayment;

    @JsonProperty("created_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    @JsonProperty("branch_code")
    private String branchCode;
}
