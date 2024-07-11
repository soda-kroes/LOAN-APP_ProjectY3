package com.java.rupp.loan_application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.rupp.loan_application.dto.LoanResponseDTO;
import com.java.rupp.loan_application.model.User;
import com.java.rupp.loan_application.utils.HttpClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${base_url}")
    String BaseUrl;

    @GetMapping("/user")
    public String userPage(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        String roleName = (String) session.getAttribute("roleName");

        if (loggedIn != null && loggedIn) {
            if ("ADMIN".equals(roleName)) {
                // Allow access for users with the role "ADMIN"
                return "user/user";
            } else {
                // Redirect to another page or return an error message for non-admin users
                return "redirect:/access-denied";
            }
        }

        return "redirect:/login";
    }



    private User getUserFromSession(HttpSession session) {
        int userId = (Integer) session.getAttribute("userId");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        String roleName = (String) session.getAttribute("roleName");
        System.out.println("roleName: " + roleName);
        return new User(userId, firstName, lastName,roleName);
    }


    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        // Clear session attributes or invalidate the session
        session.invalidate();
        // Create a RedirectView to the login page
        RedirectView redirectView = new RedirectView("/login-page");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return redirectView;
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        System.out.println("Logged in: " + loggedIn);

        if (loggedIn != null && loggedIn) {
            User user = getUserFromSession(session);
            model.addAttribute("user", user);
            return "dashboard";
        }

        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String changePassword(HttpSession session) {
        Boolean changePassWordCode = (Boolean) session.getAttribute("changePassWordCode");
        if (changePassWordCode != null && changePassWordCode) {
            return "auth/change-password";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login-page";
    }

    @GetMapping("/register")
    public String showRegister(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        if (loggedIn != null && loggedIn) {
            return "loan/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/viewLoan")
    public String showLoan(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        System.out.println(loggedIn);
        if (loggedIn != null && loggedIn) {
            return "loan/view-list";
        }
        return "redirect:/login";
    }

    @GetMapping("/report")
    public String showReport(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        if (loggedIn != null && loggedIn) {
            return "loan/report";
        }
        return "redirect:/login";
    }

    @GetMapping("/edit")
    public ModelAndView editForm(@RequestParam long loanId, Map<String, Object> map, Model model, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        if (loggedIn != null && loggedIn) {
            try {
                String url = BaseUrl + "/api/v1/loan/get-loan-by-id/" + loanId;
                String apiToken = (String) session.getAttribute("token");
                String response = HttpClient.getData(url, apiToken, "GET");
                JSONObject jsonObject = new JSONObject(response);

                String firstName = jsonObject.getJSONObject("data").getString("firstName");
                String lastName = jsonObject.getJSONObject("data").getString("lastName");
                String maritalStatus = jsonObject.getJSONObject("data").getString("maritalStatus");
                String gender = jsonObject.getJSONObject("data").getString("gender");

                String dateOfBirthString = jsonObject.getJSONObject("data").getString("dateOfBirth");
                // Parse the date string into LocalDate
                LocalDate dob = LocalDate.parse(dateOfBirthString);

                // Format the LocalDate to desired format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateOfBirth = dob.format(formatter);

                String phoneNumber = jsonObject.getJSONObject("data").getString("phoneNumber");
                String email = jsonObject.getJSONObject("data").getString("email");
                String nationalityId = jsonObject.getJSONObject("data").getString("nationalityId");
                String address = jsonObject.getJSONObject("data").getString("address");
                String nationalityImage = jsonObject.getJSONObject("data").getString("nationalityImage");
                String selfieImage = jsonObject.getJSONObject("data").getString("selfieImage");
                String currency = jsonObject.getJSONObject("data").getString("currency");
                double loanAmount = jsonObject.getJSONObject("data").getDouble("loanAmount");
                int loanTerm = jsonObject.getJSONObject("data").getInt("loanTerm");
                int loanTypeValue = jsonObject.getJSONObject("data").getJSONObject("loanType").getInt("id");
                double interestRate = jsonObject.getJSONObject("data").getJSONObject("loanType").getDouble("interestRate");
                String branchCode = jsonObject.getJSONObject("data").getJSONObject("branch").getString("code");

                System.out.println("=====================");
                System.out.println(branchCode);
                System.out.println("=====================");

                LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
                loanResponseDTO.setFirstName(firstName);
                loanResponseDTO.setLastName(lastName);
                loanResponseDTO.setMaritalStatus(maritalStatus);
                loanResponseDTO.setGender(gender);
                loanResponseDTO.setDateOfBirth(dateOfBirth);
                loanResponseDTO.setPhoneNumber(phoneNumber);
                loanResponseDTO.setEmail(email);
                loanResponseDTO.setNationalityId(nationalityId);
                loanResponseDTO.setAddress(address);
                loanResponseDTO.setNationalityImage(nationalityImage);
                loanResponseDTO.setSelfieImage(selfieImage);
                loanResponseDTO.setCurrency(currency);
                loanResponseDTO.setLoanAmount(loanAmount);
                loanResponseDTO.setLoanTerm(loanTerm);
                loanResponseDTO.setLoanTypeValue(loanTypeValue);
                loanResponseDTO.setInterestRate(interestRate);
                loanResponseDTO.setBranchCode(branchCode);

                model.addAttribute("loanResponseDTO", loanResponseDTO);

                return new ModelAndView("loan/edit");
            } catch (Exception e) {
                // Handle the exception or return an error view
            }
        }
        return new ModelAndView("redirect:/login");
    }


    @GetMapping("/detail")
    public ModelAndView detailForm(@RequestParam long loanId, Map<String,Object> map,Model model, HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute("loginCode");
        if (loggedIn!= null && loggedIn){
            try {
                String url = BaseUrl + "/api/v1/loan/get-loan-by-id/" + loanId;
                String apiToken = (String) session.getAttribute("token");
                String response = HttpClient.getData(url, apiToken, "GET");
                JSONObject jsonObject = new JSONObject(response);

                String firstName = jsonObject.getJSONObject("data").getString("firstName");
                String lastName = jsonObject.getJSONObject("data").getString("lastName");
                String maritalStatus = jsonObject.getJSONObject("data").getString("maritalStatus");
                String gender = jsonObject.getJSONObject("data").getString("gender");

                String dateOfBirthString = jsonObject.getJSONObject("data").getString("dateOfBirth");
                // Parse the date string into LocalDate
                LocalDate dob = LocalDate.parse(dateOfBirthString);

                // Format the LocalDate to desired format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dateOfBirth = dob.format(formatter);

                String phoneNumber = jsonObject.getJSONObject("data").getString("phoneNumber");
                String email = jsonObject.getJSONObject("data").getString("email");
                String nationalityId = jsonObject.getJSONObject("data").getString("nationalityId");
                String address = jsonObject.getJSONObject("data").getString("address");
                String nationalityImage = jsonObject.getJSONObject("data").getString("nationalityImage");
                String selfieImage = jsonObject.getJSONObject("data").getString("selfieImage");
                String currency = jsonObject.getJSONObject("data").getString("currency");
                double loanAmount = jsonObject.getJSONObject("data").getDouble("loanAmount");
                int loanTerm = jsonObject.getJSONObject("data").getInt("loanTerm");
                int loanTypeValue = jsonObject.getJSONObject("data").getJSONObject("loanType").getInt("id");
                double interestRate = jsonObject.getJSONObject("data").getJSONObject("loanType").getDouble("interestRate");
                String branchCode = jsonObject.getJSONObject("data").getJSONObject("branch").getString("code");

                System.out.println("=====================");
                System.out.println(branchCode);
                System.out.println("=====================");

                LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
                loanResponseDTO.setFirstName(firstName);
                loanResponseDTO.setLastName(lastName);
                loanResponseDTO.setMaritalStatus(maritalStatus);
                loanResponseDTO.setGender(gender);
                loanResponseDTO.setDateOfBirth(dateOfBirth);
                loanResponseDTO.setPhoneNumber(phoneNumber);
                loanResponseDTO.setEmail(email);
                loanResponseDTO.setNationalityId(nationalityId);
                loanResponseDTO.setAddress(address);
                loanResponseDTO.setNationalityImage(nationalityImage);
                loanResponseDTO.setSelfieImage(selfieImage);
                loanResponseDTO.setCurrency(currency);
                loanResponseDTO.setLoanAmount(loanAmount);
                loanResponseDTO.setLoanTerm(loanTerm);
                loanResponseDTO.setLoanTypeValue(loanTypeValue);
                loanResponseDTO.setInterestRate(interestRate);
                loanResponseDTO.setBranchCode(branchCode);

                model.addAttribute("loanResponseDTO", loanResponseDTO);

                return new ModelAndView("loan/detail");
            } catch (Exception e) {
                // Handle the exception or return an error view
            }
        }

        return new ModelAndView("redirect:/login");
    }




}