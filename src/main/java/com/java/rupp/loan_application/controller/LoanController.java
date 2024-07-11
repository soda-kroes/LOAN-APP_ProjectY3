package com.java.rupp.loan_application.controller;

import com.java.rupp.loan_application.dto.LoanRequestDTO;
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
@RequestMapping("/api/v1/loan")

public class LoanController {

    private final LoanService loanService;

    @Value("${base_url}")
    String BaseUrl;


    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> registerLoan(@RequestBody LoanRequestDTO loanRequestDTORequest,HttpSession session) throws Exception {
        String jsonData = loanService.createJsonRequestBody(loanRequestDTORequest);
        String apiToken = (String) session.getAttribute("token");

        System.out.println("===========================================================");
        System.out.println(jsonData);
        System.out.println("===========================================================");
        String url = BaseUrl + "/api/v1/loan/create";
        String response = HttpClient.postData(url, jsonData, apiToken,"POST");
        System.out.println("Response: " + response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/getLoan", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLoan(HttpSession session) throws Exception {
        String url = BaseUrl + "/api/v1/loan/get-all";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(url, apiToken,"GET");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable(name = "id") Long loanId,HttpSession session) throws Exception {
        if (loanId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/loan/delete/" + loanId;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.deleteData(Url, apiToken,"DELETE");

        System.out.println("====================================");
        System.out.println(response);
        System.out.println(Url);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}") // Changed to PUT mapping
    public ResponseEntity<String> updateLoanInfo(@PathVariable(name = "id") Long loanId, @RequestBody LoanRequestDTO loanRequestDTORequest,HttpSession session) throws Exception {
        if (loanId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/loan/update/" + loanId;
        String apiToken = (String) session.getAttribute("token");
        String jsonData = loanService.createJsonRequestBody(loanRequestDTORequest);
        System.out.println("=============================");
        System.out.println(jsonData);
        System.out.println("=============================");
        String response = HttpClient.updateData(Url, jsonData, apiToken, "PUT");
        System.out.println("====================================");
        System.out.println(response);
        System.out.println(Url);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getloanById/{id}")
    public ResponseEntity<String> getLoanById(@PathVariable(name = "id") Long loanId,HttpSession session) throws Exception {

        if (loanId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/loan/get-loan-by-id/" + loanId;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(Url, apiToken,"GET");
        System.out.println("====================================");
        System.out.println(response);
        System.out.println(Url);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/report", produces = "application/json")
    public ResponseEntity<String> getLoanReport(
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @RequestParam(value = "loanTypeId", required = false) Integer loanTypeId,
            HttpSession session
    ) throws Exception {
        String url = BaseUrl + "/api/v1/report/get-loan-report";
        String apiToken = (String) session.getAttribute("token");

        if (fromDate != null && !fromDate.isEmpty()) {
            url += "?fromDate=" + fromDate;
        }

        if (toDate != null && !toDate.isEmpty()) {
            if (url.contains("?")) {
                url += "&toDate=" + toDate;
            } else {
                url += "?toDate=" + toDate;
            }
        }

        if (loanTypeId != null) {
            if (url.contains("?")) {
                url += "&loanTypeId=" + loanTypeId;
            } else {
                url += "?loanTypeId=" + loanTypeId;
            }
        }

        String response = HttpClient.getData(url, apiToken, "GET");
        System.out.println("==============================================");
        System.out.println(url);
        System.out.println(response);
        System.out.println("==============================================");
        return ResponseEntity.ok(response);
    }
}
