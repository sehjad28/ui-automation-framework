/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import net.minidev.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public  class CreateCompany {

    static Faker faker = new Faker();
    static Map<String, String> contentMap = null;

    public static Map<String, String> createCompany(HashMap<String, String> companydetails) throws IOException {
        JSONObject ccObject = new JSONObject();
        HttpPost ccPost = null;

        LocalDate today = LocalDate.now();
        String updatedOn = today + "T15:35:12.793Z";
        String createdOn = today + "T15:35:12.793Z";

        String taxId = "12-3456799";
        if (companydetails.get("taxId") != null) {
            taxId = companydetails.get("taxId");
        }

        String name = faker.company().name();
        if (companydetails.get("name") != null) {
            name = companydetails.get("name");
        }

        ccObject.put("accountEnabled", true);
        ccObject.put("accountNumber", "");
        ccObject.put("codePriorityId", 1);
        ccObject.put("companyOwnershipTypeId", 1);
        ccObject.put("companyTypeId", 1);
        ccObject.put("createdBy", "Admin");
        ccObject.put("createdOn", createdOn);
        ccObject.put("legalBusinessName", "auto " + name);
        ccObject.put("name", "auto " + name);
        ccObject.put("numberOfTechnicians", 10);
        ccObject.put("reportingName", "auto " + name);
        ccObject.put("serviceInvoiceAllowedStatus", true);
        ccObject.put("serviceJobsAllowedStatus", true);
        ccObject.put("status", 0);
        ccObject.put("taxId", taxId);
        ccObject.put("updatedBy", "Admin");
        ccObject.put("updatedOn", updatedOn);

        StringEntity ccEntity = new StringEntity(ccObject.toString());

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            ccPost = new HttpPost("http://qa-accel-core-ms-nlb-6f80b2e3f1499e22.elb.us-east-1.amazonaws.com/acceleratecore/api/companies");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            ccPost = new HttpPost("http://preprod-accel-core-ms-nlb-5676ec6a980c92af.elb.us-east-1.amazonaws.com/acceleratecore/api/companies");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            ccPost = new HttpPost("http://preprod2-accel-fsm-ms-nlb-0d89d04fe2a37888.elb.us-east-1.amazonaws.com/AccelFsmMs/api/companies");
        }

        ccPost.setEntity(ccEntity);
        ccPost.setHeader("Accept", "application/json");
        ccPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(ccPost);

        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            Map<String, String> contentMap = new ObjectMapper().readValue(content, HashMap.class);
            /*jsonObject = new JSONObject(contentMap); // serviceRequestReq -> serviceRequestId
            Map<String, String> contentsubmap = (Map<String, String>) jsonObject.get("serviceRequestReq");
            serviceRequestId = contentsubmap.get("serviceRequestId").toString();*/
            return contentMap;
        }

        return contentMap;
    }

    public static void main(String[] args) throws IOException {
        CreateCompany cc = new CreateCompany();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("taxId", "12-3456799");

        System.out.println(cc.createCompany(hashMap));
    }
}