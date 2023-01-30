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
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class CreateServiceRequest {

    Faker faker = new Faker();
    /*String serviceRequestId = null;
    String content = null;*/
    Map<String, String> contentMap = null;

    public Map<String, String> createServiceRequest(HashMap<String, String> servdetails) throws IOException {
        JSONObject jsonObject = new JSONObject();
        HttpPost createjobrequest = null;

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

        String scheduledServiceDate = today.toString();
        if (servdetails.get("scheduledServiceDate") != null) {
            scheduledServiceDate = servdetails.get("scheduledServiceDate");
        }

        String slotBegin = "16:00:00";
        if (servdetails.get("slotBegin") != null) {
            slotBegin = servdetails.get("slotBegin");
        }

        String spAccountNum = "73418";
        if (servdetails.get("spAccountNum") != null) {
            spAccountNum = servdetails.get("spAccountNum");
        }

        String zipCode = "30004";
        if (servdetails.get("zipCode") != null) {
            zipCode = servdetails.get("zipCode");
        }

        String contractNumber = null;
        if (servdetails.get("contractNumber") != null) {
            contractNumber = servdetails.get("contractNumber");
        }

        jsonObject.put("addressLine1", faker.address().streetAddress());
        jsonObject.put("brandId", 1);
        jsonObject.put("city", "NewYork");
        jsonObject.put("email", faker.internet().emailAddress());
        jsonObject.put("firstName", "Automation" + faker.name().firstName());
        jsonObject.put("itemId", 1);
        jsonObject.put("lastName", faker.name().lastName());
        jsonObject.put("problemDesc", "Not working");
        jsonObject.put("scheduledServiceDate", scheduledServiceDate);
        jsonObject.put("serviceRequestType", "FFS");
        jsonObject.put("slotBegin", slotBegin);
        jsonObject.put("tradeLocationId", null);
        jsonObject.put("tradeProblemCodeMapId", 6);
        jsonObject.put("spAccountNum", spAccountNum);
        jsonObject.put("zipCode", zipCode);
        jsonObject.put("country", "US");
        jsonObject.put("mobileNumber", faker.phoneNumber().cellPhone());
        jsonObject.put("modelNumber", null);
        jsonObject.put("phoneNumber", null);
        jsonObject.put("serialNumber", null);
        jsonObject.put("slotEnd", "20:00:00");
        jsonObject.put("specialInstructions", "Watch for a dog");
        jsonObject.put("state", "NY");
        jsonObject.put("contractNumber", contractNumber);
        jsonObject.put("itemProblemCode", null);
        jsonObject.put("itemProblemDescription", null);
        jsonObject.put("itemTypeId", null);
        jsonObject.put("itemTypeProblemCode", null);
        jsonObject.put("itemTypeProblemDescription", null);
        jsonObject.put("spCommission", true);
        jsonObject.put("affinityPartner", null);
        jsonObject.put("serviceJobTypeId", 1);

        StringEntity entity = new StringEntity(jsonObject.toString());

//        createjobrequest = new HttpPost("http://qa-accel-fsm-ms-nlb-2935e21df04a4359.elb.us-east-1.amazonaws.com/AccelFsmMs/api/servicerequests");

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            createjobrequest = new HttpPost("http://qa-accel-fsm-ms-nlb-2935e21df04a4359.elb.us-east-1.amazonaws.com/AccelFsmMs/api/servicerequests");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            createjobrequest = new HttpPost("http://preprod-accel-fsm-ms-nlb-2253796b4271087d.elb.us-east-1.amazonaws.com/AccelFsmMs/api/servicerequests");

        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            createjobrequest = new HttpPost("http://preprod2-accel-fsm-ms-nlb-0d89d04fe2a37888.elb.us-east-1.amazonaws.com/AccelFsmMs/api/servicerequests");
        }
      
        createjobrequest.setEntity(entity);
        createjobrequest.setHeader("Accept", "application/json");
        createjobrequest.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(createjobrequest);

/*        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode < 200 || statusCode >= 300) {
            throw new WebRequestBadStatusException(statusCode);
        }*/

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
        CreateServiceRequest csj = new CreateServiceRequest();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("scheduledServiceDate", "2021-01-28");
        hashMap.put("slotBegin", "16:00:00");
        hashMap.put("zipCode", "11004");
        hashMap.put("spAccountNum", "59027");
        hashMap.put("contractNumber", "10563354.0");

        System.out.println(csj.createServiceRequest(hashMap));
    }
}