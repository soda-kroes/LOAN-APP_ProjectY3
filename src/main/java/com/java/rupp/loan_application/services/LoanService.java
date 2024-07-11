package com.java.rupp.loan_application.services;

import com.java.rupp.loan_application.dto.LoanRequestDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LoanService {
    public String createJsonRequestBody(LoanRequestDTO loanRequestDTORequest) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("last_name", loanRequestDTORequest.getLastName());
            jsonObject.put("first_name", loanRequestDTORequest.getFirstName());
            jsonObject.put("marital_status", loanRequestDTORequest.getMaritalStatus());
            jsonObject.put("date_of_birth", loanRequestDTORequest.getDateOfBirth());
            jsonObject.put("phone_number", loanRequestDTORequest.getPhoneNumber());
            jsonObject.put("gender", loanRequestDTORequest.getGender());
            jsonObject.put("email", loanRequestDTORequest.getEmail());
            jsonObject.put("nationality_id", loanRequestDTORequest.getNationalityId());
            jsonObject.put("nationality_image", loanRequestDTORequest.getNationalityImage());
            jsonObject.put("selfie_image", loanRequestDTORequest.getSelfieImage());
            jsonObject.put("address", loanRequestDTORequest.getAddress());
            jsonObject.put("loan_amount", loanRequestDTORequest.getLoanAmount());
            jsonObject.put("loan_type", loanRequestDTORequest.getLoanTypeId());
            jsonObject.put("loan_term", loanRequestDTORequest.getLoanTerm());
            jsonObject.put("currency", loanRequestDTORequest.getCurrency());
            jsonObject.put("created_date", loanRequestDTORequest.getCreatedDate());
            jsonObject.put("branch_code", loanRequestDTORequest.getBranchCode());
            jsonObject.put("loan_type_id", loanRequestDTORequest.getLoanTypeId());

            LocalDateTime createdDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createdDateString = createdDate.format(formatter);
            jsonObject.put("created_date", createdDateString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}