package com.java.rupp.loan_application.controller;

import com.java.rupp.loan_application.services.LoanService;
import com.java.rupp.loan_application.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/master-data")
public class MasterDataController {
    private  final LoanService loanService;

    @Value("${base_url}")
    String BaseUrl;


    @GetMapping("/getLoanType")
    public ResponseEntity<String> getLoanType(HttpSession session) throws Exception {
        String Url = BaseUrl + "/api/v1/loan-type/get-all";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(Url, apiToken,"GET");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getInterestRate/{loanTypeId}")
    public ResponseEntity<String> getInterestRate(@PathVariable(name = "loanTypeId") Long loanTypeId,HttpSession session) throws Exception {
        if (loanTypeId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanTypeId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/loan-type/get-interest-rate-by-id/" + loanTypeId;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(Url, apiToken,"GET");
        System.out.println("====================================");
        System.out.println(response);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getBranch")
    public ResponseEntity<String> getBranch(HttpSession session) throws Exception {
        String Url = BaseUrl + "/api/v1/branch/get-all";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(Url, apiToken,"GET");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getRole")
    public ResponseEntity<String> getRole(HttpSession session) throws Exception {
        String Url = BaseUrl + "/api/v1/role/get-all";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(Url, apiToken,"GET");
        return ResponseEntity.ok(response);
    }
}
