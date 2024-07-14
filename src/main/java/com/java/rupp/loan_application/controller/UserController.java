package com.java.rupp.loan_application.controller;


import com.java.rupp.loan_application.dto.LoanRequestDTO;
import com.java.rupp.loan_application.dto.UserRequestDTO;
import com.java.rupp.loan_application.services.UserService;
import com.java.rupp.loan_application.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    @Value("${base_url}")
    String BaseUrl;



    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDTO userRequestDTO, HttpSession session) throws Exception {
        String jsonData = userService.createJsonRequestBody(userRequestDTO);
        System.out.println("===========================================================");
        System.out.println(jsonData);
        System.out.println("===========================================================");
        String url = BaseUrl + "/api/v1/auth/register";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.postData(url, jsonData, apiToken,"POST");
        System.out.println("Response: " + response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> editUser(@PathVariable(name = "id") Long valueUserId, @RequestBody UserRequestDTO userRequestDTO, HttpSession session) throws Exception {
        String jsonData = userService.createJsonRequestEditBody(userRequestDTO);

        System.out.println("===========================================================");
        System.out.println(jsonData);
        System.out.println(valueUserId);
        System.out.println("===========================================================");
        String url = BaseUrl + "/api/v1/user/update/"+valueUserId;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.postData(url, jsonData, apiToken,"PUT");
        System.out.println("Response: " + response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getLoan(HttpSession session) throws Exception {
        String url = BaseUrl + "/api/v1/user/get-all";
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(url, apiToken,"GET");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<String> getUserById(@PathVariable(name = "id") Long userId,HttpSession session) throws Exception {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/user/get-by-id/" + userId;

        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.deleteData(Url, apiToken,"GET");
        System.out.println("====================================");
        System.out.println(response);
        System.out.println(Url);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable(name = "id") Long userId,HttpSession session) throws Exception {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: loanId parameter is missing or empty.");
        }
        String Url = BaseUrl + "/api/v1/user/delete/" + userId;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.deleteData(Url, apiToken,"DELETE");
        System.out.println("====================================");
        System.out.println(response);
        System.out.println(Url);
        System.out.println("====================================");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> retSetPassword(@RequestParam(name = "userId") Long userId,HttpSession session) throws Exception {
        String password="123Bank!";
        String url = BaseUrl + "/api/v1/user/reset-pass?id="+userId+"&"+"password="+password;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(url, apiToken,"POST");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> EnableUser(@PathVariable(name = "id") Long id,HttpSession session) throws Exception {
        String url = BaseUrl + "/api/v1/user/enable/"+id;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(url, apiToken,"GET");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> DisableUser(@PathVariable(name = "id") Long id,HttpSession session) throws Exception {
        String url = BaseUrl + "/api/v1/user/disable/"+id;
        String apiToken = (String) session.getAttribute("token");
        String response = HttpClient.getData(url, apiToken,"GET");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }
}
