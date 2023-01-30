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

public class CreateSpSiteArea {

    static Faker faker = new Faker();
    static Map<String, String> contentMap = null;

    public static Map<String, String> createSpSiteArea(HashMap<String, String> companydetails) throws IOException {

        JSONObject spSiteAreasObject = new JSONObject();
        HttpPost spSiteAreasPost = null;

        LocalDate today = LocalDate.now();
        String updatedOn = today + "T23:40:43.545Z";
        String createdOn = today + "T23:40:43.545Z";

        spSiteAreasObject.put("companySiteId", companydetails.get("companySiteId"));
        spSiteAreasObject.put("createdBy", "Admin");
        spSiteAreasObject.put("createdOn", createdOn);
        spSiteAreasObject.put("defaultSite", true);
        spSiteAreasObject.put("name", companydetails.get("name"));
        spSiteAreasObject.put("status", 0);
        spSiteAreasObject.put("updatedBy", "Admin");
        spSiteAreasObject.put("updatedOn", updatedOn);

        StringEntity ccEntity = new StringEntity(spSiteAreasObject.toString());

        if (System.getProperty("environment").equalsIgnoreCase("qa")) {
            spSiteAreasPost = new HttpPost("http://qa-accel-servicepartner-ms-nlb-75715ae547cfb7e0.elb.us-east-1.amazonaws.com/servicepartner/api/sp-site-areas");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod")) {
            spSiteAreasPost = new HttpPost("http://preprod-accel-core-ms-nlb-5676ec6a980c92af.elb.us-east-1.amazonaws.com/acceleratecore/api/sp-site-areas");
        } else if (System.getProperty("environment").equalsIgnoreCase("preprod2")) {
            spSiteAreasPost = new HttpPost("http://preprod2-accel-fsm-ms-nlb-0d89d04fe2a37888.elb.us-east-1.amazonaws.com/AccelFsmMs/api/sp-site-areas");
        }

        spSiteAreasPost.setEntity(ccEntity);
        spSiteAreasPost.setHeader("Accept", "application/json");
        spSiteAreasPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(spSiteAreasPost);

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
        CreateSpSiteArea cspsa = new CreateSpSiteArea();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("companySiteId", "1026");
        hashMap.put("name", "auto Schumm-Feeney");

        System.out.println(cspsa.createSpSiteArea(hashMap));
    }
}