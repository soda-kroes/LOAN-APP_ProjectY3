package com.java.rupp.loan_application.services;

import com.java.rupp.loan_application.dto.UserRequestDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String createJsonRequestBody(UserRequestDTO userRequestDTO) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", userRequestDTO.getFirstname());
            jsonObject.put("lastname", userRequestDTO.getLastname());
            jsonObject.put("gender", userRequestDTO.getGender());
            jsonObject.put("email", userRequestDTO.getEmail());
            jsonObject.put("password", userRequestDTO.getPassword());
            jsonObject.put("user_role_id", userRequestDTO.getUserRoleId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public String createJsonRequestEditBody(UserRequestDTO userRequestDTO) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", userRequestDTO.getFirstname());
            jsonObject.put("lastname", userRequestDTO.getLastname());
            jsonObject.put("gender", userRequestDTO.getGender());
            jsonObject.put("email", userRequestDTO.getEmail());
            jsonObject.put("user_role_id", userRequestDTO.getUserRoleId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
