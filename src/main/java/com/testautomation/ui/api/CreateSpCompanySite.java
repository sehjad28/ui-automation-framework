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

public class CreateSpCompanySite {

    static Faker faker = new Faker();
    static Map<String, String> contentMap = null;

    public static Map<String, String> createSpCompanySite(HashMap<String, String> companydetails) throws IOException {

        JSONObject spCompanySiteObject = new JSONObject();
        HttpPost spCompanySitePost = null;

        LocalDate today = LocalDate.now();
        String updatedOn = today + "T15:35:12.793Z";
        String createdOn = today + "T15:35:12.793Z";

        spCompanySiteObject.put("companyId", companydetails.get("companyId"));
        spCompanySiteObject.put("createdBy", "Admin");
        spCompanySiteObject.put("createdOn", createdOn);
        spCompanySiteObject.put("defaultCompany", true);
        spCompanySiteObject.put("name", companydetails.get("name"));
        spCompanySiteObject.put("status", 0);
        spCompanySiteObject.put("updatedBy", "Admin");
        spCompanySiteObject.put("updatedOn", updatedOn);

        StringEntity ccEntity = new StringEntity(spCompanySiteObject.toString());

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            spCompanySitePost = new HttpPost("http://qa-accel-servicepartner-ms-nlb-75715ae547cfb7e0.elb.us-east-1.amazonaws.com/servicepartner/api/sp-company-sites");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            spCompanySitePost = new HttpPost("http://preprod-accel-core-ms-nlb-5676ec6a980c92af.elb.us-east-1.amazonaws.com/acceleratecore/api/sp-company-sites");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            spCompanySitePost = new HttpPost("http://preprod2-accel-fsm-ms-nlb-0d89d04fe2a37888.elb.us-east-1.amazonaws.com/AccelFsmMs/api/sp-company-sites");
        }

        spCompanySitePost.setEntity(ccEntity);
        spCompanySitePost.setHeader("Accept", "application/json");
        spCompanySitePost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(spCompanySitePost);

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
        CreateSpCompanySite cspcs = new CreateSpCompanySite();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("companyId", "300273");
        hashMap.put("name", "auto Schumm-Feeney");

        System.out.println(cspcs.createSpCompanySite(hashMap));
    }
}