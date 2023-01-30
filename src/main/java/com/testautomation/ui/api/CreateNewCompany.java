/*
 * @author Sampad Rout
 * (C) Copyright 2021 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.api;

import net.minidev.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateNewCompany {

    public HashMap<String, String> createnewcompany(HashMap<String, String> companydetails) throws IOException {

        JSONObject jsonObject;

        // Create company
        Map<String, String> company = CreateCompany.createCompany(companydetails);

        jsonObject = new JSONObject(company);
        int companyId = (int) jsonObject.get("companyId");
        String name = (String) jsonObject.get("name");

        companydetails.put("companyId", String.valueOf(companyId));
        companydetails.put("name", name);

        // create sp site
        Map<String, String> spCompanySite = CreateSpCompanySite.createSpCompanySite(companydetails);

        jsonObject = new JSONObject(spCompanySite);
        int companySiteId = (int) jsonObject.get("companySiteId");
        companydetails.put("companySiteId", String.valueOf(companySiteId));

        // create sp sire area
        Map<String, String> spSiteArea = CreateSpSiteArea.createSpSiteArea(companydetails);

        // create sp communication
        Map<String, String> spCommunication = CreateSpCommunication.createSpCommunication(companydetails);

        // create sp address
        Map<String, String> spAddress = CreateSpAddress.createSpAddress(companydetails);

        return companydetails;
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, String> companydetails = new HashMap<>();

        CreateNewCompany obj = new CreateNewCompany();
        Map<String, String> company = obj.createnewcompany(companydetails);
        System.out.println(company.get("companyId"));
        System.out.println(company.get("companySiteId"));
        System.out.println(company.get("name"));
    }
}