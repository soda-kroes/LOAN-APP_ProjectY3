package com.java.rupp.loan_application.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ACCEPT_HEADER = "Accept";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String CONNECTION_HEADER = "Connection";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getData(String url, String token, String requestMethod) throws Exception {
        URL apiUrl = new URL(url);

        TrustCert trustCert = new TrustCert();
        trustCert.trustAllCertificates();

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, "application/json");
        connection.setRequestProperty(ACCEPT_HEADER, "application/json");
        connection.setRequestProperty(CONNECTION_HEADER, "Keep-Alive");
        connection.setRequestProperty(AUTHORIZATION_HEADER, "Bearer " + token);

        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String jsonResponse = response.toString().replaceAll("^\"|\"$", "").replace("\\", "");
        String modifiedJsonResponse = jsonResponse.replace("[\"[", "[").replace("]\"]", "]");

        return modifiedJsonResponse;
    }

    public static String deleteData(String url, String token, String requestMethod) throws Exception {
        URL apiUrl = new URL(url);

        TrustCert trustCert = new TrustCert();
        trustCert.trustAllCertificates();

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        connection.setRequestMethod(requestMethod); // Set the request method dynamically

        connection.setRequestProperty(CONTENT_TYPE_HEADER, "application/json");
        connection.setRequestProperty(ACCEPT_HEADER, "application/json");
        connection.setRequestProperty(CONNECTION_HEADER, "Keep-Alive");
        connection.setRequestProperty(AUTHORIZATION_HEADER, "Bearer " + token);

        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String jsonResponse = response.toString().replaceAll("^\"|\"$", "").replace("\\", "");
        String modifiedJsonResponse = jsonResponse.replace("[\"[", "[").replace("]\"]", "]");

        return modifiedJsonResponse;
    }

    public static String postData(String url, String postJsonData, String token, String requestMethod) throws Exception {
        URL apiUrl = new URL(url);

        TrustCert trustCert = new TrustCert();
        trustCert.trustAllCertificates();

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        byte[] postDataBytes = postJsonData.getBytes(StandardCharsets.UTF_8);
        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, "application/json; charset=UTF-8");
        connection.setRequestProperty(ACCEPT_HEADER, "application/json");
        connection.setRequestProperty(CONTENT_LENGTH_HEADER, String.valueOf(postDataBytes.length));
        connection.setRequestProperty(CONNECTION_HEADER, "Keep-Alive");
        connection.setRequestProperty(AUTHORIZATION_HEADER, "Bearer " + token);

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            writer.write(postJsonData);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    public static String updateData(String url, String postJsonData, String token, String requestMethod) throws Exception {
        URL apiUrl = new URL(url);

        TrustCert trustCert = new TrustCert();
        trustCert.trustAllCertificates();

        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);

        byte[] postDataBytes = postJsonData.getBytes(StandardCharsets.UTF_8);

        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty(CONTENT_TYPE_HEADER, "application/json; charset=UTF-8");
        connection.setRequestProperty(ACCEPT_HEADER, "application/json");
        connection.setRequestProperty(CONTENT_LENGTH_HEADER, String.valueOf(postDataBytes.length));
        connection.setRequestProperty(CONNECTION_HEADER, "Keep-Alive");
        connection.setRequestProperty(AUTHORIZATION_HEADER, "Bearer " + token);

        try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
            writer.write(postJsonData);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }
}