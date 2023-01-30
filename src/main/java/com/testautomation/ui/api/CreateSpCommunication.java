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
import java.util.Random;

public class CreateSpCommunication {

    static Faker faker = new Faker();

    static Random random = new Random();
    static Map<String, String> contentMap = null;

    public static Map<String, String> createSpCommunication(HashMap<String, String> companydetails) throws IOException {

        JSONObject spCommunicationObject = new JSONObject();
        HttpPost spCommunicationPost = null;

        LocalDate today = LocalDate.now();
        String updatedOn = today + "T23:40:43.545Z";
        String createdOn = today + "T23:40:43.545Z";

        String phone = String.format("%d", (long) Math.floor(Math.random() * 9_000_000_00L) + 8_000_000_000L);
        if (companydetails.get("phone") != null) {
            phone = companydetails.get("phone");
        }

        String mobile = String.format("%d", (long) Math.floor(Math.random() * 9_000_000_00L) + 8_000_000_000L);
        if (companydetails.get("mobile") != null) {
            mobile = companydetails.get("mobile");
        }

        String primaryEmailAddress = faker.internet().emailAddress();
        if (companydetails.get("primaryEmailAddress") != null) {
            primaryEmailAddress = companydetails.get("primaryEmailAddress");
        }

        String secondaryEmailAddress = faker.internet().emailAddress();
        if (companydetails.get("secondaryEmailAddress") != null) {
            secondaryEmailAddress = companydetails.get("secondaryEmailAddress");
        }

        spCommunicationObject.put("alternatePhone", phone);
        spCommunicationObject.put("communicationType", "2");
        spCommunicationObject.put("companyId", companydetails.get("companyId"));
        spCommunicationObject.put("companySiteId", companydetails.get("companySiteId"));
        spCommunicationObject.put("createdBy", "Admin");
        spCommunicationObject.put("createdOn", createdOn);
        spCommunicationObject.put("enableSms", true);
        spCommunicationObject.put("faxNo", "");
        spCommunicationObject.put("mobile", mobile);
        spCommunicationObject.put("phone", phone);
        spCommunicationObject.put("primaryEmailAddress", primaryEmailAddress);
        spCommunicationObject.put("secondaryEmailAddress", secondaryEmailAddress);
        spCommunicationObject.put("spCommunicationId", "");
        spCommunicationObject.put("spContactId", "");
        spCommunicationObject.put("status", 0);
        spCommunicationObject.put("updatedBy", "Admin");
        spCommunicationObject.put("updatedOn", updatedOn);

        StringEntity ccEntity = new StringEntity(spCommunicationObject.toString());

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            spCommunicationPost = new HttpPost("http://qa-accel-servicepartner-ms-nlb-75715ae547cfb7e0.elb.us-east-1.amazonaws.com/servicepartner/api/sp-communications");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            spCommunicationPost = new HttpPost("http://preprod-accel-core-ms-nlb-5676ec6a980c92af.elb.us-east-1.amazonaws.com/acceleratecore/api/sp-communications");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            spCommunicationPost = new HttpPost("http://preprod2-accel-fsm-ms-nlb-0d89d04fe2a37888.elb.us-east-1.amazonaws.com/AccelFsmMs/api/sp-communications");
        }

        spCommunicationPost.setEntity(ccEntity);
        spCommunicationPost.setHeader("Accept", "application/json");
        spCommunicationPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(spCommunicationPost);

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
        CreateSpCommunication cspc = new CreateSpCommunication();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("companyId", "300273");
        hashMap.put("companySiteId", "1026");

        System.out.println(cspc.createSpCommunication(hashMap));

    }
}