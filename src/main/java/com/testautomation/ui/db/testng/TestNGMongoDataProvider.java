/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.db.testng;

import com.testautomation.ui.db.annotations.GetDataFromMongo;
import com.testautomation.ui.db.dbconnect.MongoDBConfigReader;
import com.testautomation.ui.db.dbconnect.MongoTestCaseReader;
import com.testautomation.ui.db.model.GetDataFromMongoModel;
import com.testautomation.ui.db.model.MongoConnectionData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.DataProvider;

public class TestNGMongoDataProvider {
    @DataProvider(name = "dataProviderMongo")
    public static Object[][] getTestData(Method m) {
        Object[][] returndata = null;
        Annotation[] annotations = m.getDeclaredAnnotations();
        GetDataFromMongo datafmdb = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof GetDataFromMongo) {
                datafmdb = (GetDataFromMongo) annotation;
            }
        }
        try {
            MongoConnectionData mongoconndata = new MongoConnectionData();
            mongoconndata.setHostname("automationdb.qa.accelerate.cinchhs.com");
            mongoconndata.setUsername("acceladmin");
            mongoconndata.setPassword("l3xr06lqhtzKc");
            mongoconndata.setPort("27017");
            String collectionName = datafmdb.collectionName().replace("environment", System.getProperty("environment"));
            String environment = datafmdb.envName().replace("environment", System.getProperty("environment"));
            GetDataFromMongoModel getdatamodel = new GetDataFromMongoModel();
            getdatamodel.setAppName(datafmdb.appName());

            System.out.println("Annotation:" + datafmdb.collectionName());
            System.out.println("collectionName:" + collectionName);

            getdatamodel.setEnvName(environment);
            getdatamodel.setDatatype(datafmdb.dataType());
            getdatamodel.setDbName(datafmdb.dbName());
            getdatamodel.setCollectionName(collectionName);

            if (datafmdb.dataType().contains("testcaseData")) {
                getdatamodel.setTcName(datafmdb.tcName());
                List<Map<String, String>> mongoreslist = MongoTestCaseReader.queryTestDataMongo(mongoconndata, getdatamodel);
                returndata = new Object[mongoreslist.size()][1];

                for (int listindex = 0; listindex <= mongoreslist.size() - 1; listindex++) {
                    Map<String, Object> tempMap = new HashMap<>();
                    for (Entry<String, String> entry : mongoreslist.get(listindex).entrySet()) {
                        tempMap.put(entry.getKey(), entry.getValue());
                        returndata[listindex] = new Object[]{tempMap};
                    }
                }
            } else if (datafmdb.dataType().contains("configData")) {
//			getdatamodel.setTcName(datafmdb.tcName());
                MongoDBConfigReader.loadPropertiesFromMongoDB(mongoconndata, getdatamodel);
                returndata = new Object[1][0];
            }

        } catch (Exception ex) {
            System.out.println("Exception:" + ex);
        }
        return returndata;
    }
}