/*
 * @author Sampad Rout
 * (C) Copyright 2021 by Cinch Home Services, Inc.
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

public class CreateSpAddress {

    static Faker faker = new Faker();
    static Map<String, String> contentMap = null;

    public static Map<String, String> createSpAddress(HashMap<String, String> companydetails) throws IOException {

        JSONObject spAddressObject = new JSONObject();
        HttpPost spAddressPost = null;

        LocalDate today = LocalDate.now();
        String updatedOn = today + "T23:40:43.545Z";
        String createdOn = today + "T23:40:43.545Z";

        String city = "New York";
        if (companydetails.get("city") != null) {
            city = companydetails.get("city");
        }

        String state = "New York";
        if (companydetails.get("state") != null) {
            state = companydetails.get("state");
        }

        String zip = "10024";
        if (companydetails.get("zip") != null) {
            zip = companydetails.get("zip");
        }

        String zip4 = "0301";
        if (companydetails.get("zip4") != null) {
            zip4 = companydetails.get("zip4");
        }

        spAddressObject.put("address1", faker.address().secondaryAddress());
        spAddressObject.put("address2", faker.address().streetAddress());
        spAddressObject.put("address3", "");
        spAddressObject.put("city", city);
        spAddressObject.put("companyId", companydetails.get("companyId"));
        spAddressObject.put("companySiteId", companydetails.get("companySiteId"));
        spAddressObject.put("country", "");
        spAddressObject.put("countyCode", "");
        spAddressObject.put("createdBy", "Admin");
        spAddressObject.put("createdOn", createdOn);
        spAddressObject.put("spAddressId", "");
        spAddressObject.put("spAddressTypeId", 1);
        spAddressObject.put("spContactId", "");
        spAddressObject.put("state", state);
        spAddressObject.put("status", 0);
        spAddressObject.put("updatedBy", "Admin");
        spAddressObject.put("updatedOn", updatedOn);
        spAddressObject.put("zip", zip);
        spAddressObject.put("zip4", zip4);

        StringEntity ccEntity = new StringEntity(spAddressObject.toString());

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            spAddressPost = new HttpPost("http://qa-accel-servicepartner-ms-nlb-75715ae547cfb7e0.elb.us-east-1.amazonaws.com/servicepartner/api/sp-addresses");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            spAddressPost = new HttpPost("http://preprod-accel-sp-ms-nlb-0800513de191f9ab.elb.us-east-1.amazonaws.com/servicepartner/api/sp-addresses");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            spAddressPost = new HttpPost("http://preprod2-accel-sp-ms-nlb-c1a52181022583d9.elb.us-east-1.amazonaws.com/servicepartner/api/sp-addresses");
        }

        spAddressPost.setEntity(ccEntity);
        spAddressPost.setHeader("Accept", "application/json");
        spAddressPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(spAddressPost);
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
        CreateSpAddress cspa = new CreateSpAddress();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("companyId", "300273");
        hashMap.put("companySiteId", "1026");

        System.out.println(cspa.createSpAddress(hashMap));
    }
}