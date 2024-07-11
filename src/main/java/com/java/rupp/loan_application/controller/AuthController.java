package com.java.rupp.loan_application.controller;


import com.java.rupp.loan_application.model.UserRequestLoginDTO;
import com.java.rupp.loan_application.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${base_url}")
    String BaseUrl;

    @RequestMapping(value = "/check-login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> checkLogin(@RequestBody UserRequestLoginDTO requestLoginDTO, HttpSession session) throws Exception {
        String jsonData = createJsonRequestBody(requestLoginDTO);
        System.out.println("==================================");
        System.out.println(jsonData);
        System.out.println("==================================");
        String url = BaseUrl + "/api/v1/auth/login";
        String response = HttpClient.postData(url, jsonData, null, "POST");
        System.out.println("=============");
        System.out.println("Response: " + response);
        System.out.println("============");
        try {
            JSONObject jsonObject = new JSONObject(response);
            String errCode = jsonObject.optString("errorCode");
            String token = jsonObject.optString("token");
            JSONObject userObject = jsonObject.optJSONObject("user");

            if (errCode.equals("200") && userObject != null) {
                int userId = userObject.optInt("id");
                String firstname = userObject.optString("firstname");
                String lastname = userObject.optString("lastname");
                JSONArray userRolesArray = userObject.optJSONArray("userRoles");
                String roleName = userRolesArray != null && userRolesArray.length() > 0 ? userRolesArray.getJSONObject(0).optString("name") : "";

                session.setAttribute("loginCode", true);
                session.setAttribute("email", requestLoginDTO.getEmail());
                session.setAttribute("userId", userId);
                session.setAttribute("firstName", firstname);
                session.setAttribute("lastName", lastname);
                session.setAttribute("roleName", roleName);
                session.setAttribute("token", token); // Corrected attribute name
            } else if (errCode.equals("203")) {
                session.setAttribute("changePassWordCode", true);
                session.setAttribute("token", token);
                session.setAttribute("changePasswordUserId", userObject.optInt("id"));
                session.setAttribute("email", requestLoginDTO.getEmail());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> passwordMap, HttpSession session) throws Exception {
        Integer userId = (Integer) session.getAttribute("changePasswordUserId");
        String apiToken = (String) session.getAttribute("token"); // Corrected attribute name

        System.out.println("====================");
        System.out.println("userId: " + userId);
        System.out.println("password: " + passwordMap.get("password"));
        System.out.println("apiToken: " + apiToken);
        System.out.println("====================");

        String url = BaseUrl + "/api/v1/user/change-default-pass?id=" + userId + "&" + "password=" + passwordMap.get("password");
        String response = HttpClient.getData(url, apiToken, "POST");
        System.out.println("===========================================");
        System.out.println("Response: " + response);
        System.out.println("BaseUrl : " + url);
        System.out.println("===========================================");
        return ResponseEntity.ok(response);
    }

    private String createJsonRequestBody(UserRequestLoginDTO requestLoginDTO) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", requestLoginDTO.getEmail());
            jsonObject.put("password", requestLoginDTO.getPassword());
        } catch (JSONException e) {
        }
        return jsonObject.toString();
    }



}
