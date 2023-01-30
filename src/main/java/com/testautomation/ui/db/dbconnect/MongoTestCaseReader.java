/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.db.dbconnect;

import com.testautomation.ui.db.model.GetDataFromMongoModel;
import com.testautomation.ui.db.model.MongoConnectionData;
import com.testautomation.ui.utilities.LoadLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

@SuppressWarnings({ "resource", "unchecked", "deprecation" })
public class MongoTestCaseReader {

	static Logger log = Logger.getLogger(LoadLogger.class);

	public static List<Map<String, String>> queryTestDataMongo(MongoConnectionData mongoconndata, GetDataFromMongoModel gdmodel) {
		List<Map<String, String>> testdatainList = null;
		try {
			String username = mongoconndata.getUsername();
			String password = mongoconndata.getPassword();
			String hostname = mongoconndata.getHostname();
			String dbname = gdmodel.getDbName();
			String mdbcollection = gdmodel.getCollectionName();			

			// Creating a Mongo client
			MongoClientURI uri = new MongoClientURI("mongodb://" + username + ":" + password + "@" + hostname + "/?authSource=" + dbname);
			MongoClient mongoClient = new MongoClient(uri);

			// Accessing the database
			DBCollection collection = mongoClient.getDB(dbname).getCollection(mdbcollection);
			BasicDBObject getQuery = new BasicDBObject();
			getQuery.append("appName", gdmodel.getAppName());
			getQuery.append("envName", gdmodel.getEnvName());
			getQuery.append("tcName", gdmodel.getTcName());
			System.out.println("Query: " + getQuery);
			log.info("Query: " + getQuery);
			DBCursor cursor = collection.find(getQuery);

			while (cursor.hasNext()) {
				String testData = cursor.next().toMap().get("testcaseData").toString();
				ObjectMapper mapper = new ObjectMapper();
				testdatainList = mapper.readValue(testData, List.class);
				System.out.println(testdatainList);
				log.info(testdatainList);
			}
		} catch (Exception ex) {
			System.out.println(ex);
			log.error(ex);
		}
		return testdatainList;
	}

	public static void main(String[] args) {
		MongoConnectionData mongoconndata = new MongoConnectionData();
		mongoconndata.setHostname("automationdb.qa.accelerate.cinchhs.com");
		mongoconndata.setUsername("acceladmin");
		mongoconndata.setPassword("l3xr06lqhtzKc");

		GetDataFromMongoModel getdatamodel = new GetDataFromMongoModel();
		getdatamodel.setAppName("accel_customer");
		getdatamodel.setEnvName("preprod");
		getdatamodel.setTcName("C27633");
		getdatamodel.setDatatype("testcaseData");
		getdatamodel.setDbName("accel_customer");
		getdatamodel.setCollectionName("preprod_ui");
		
		queryTestDataMongo(mongoconndata,getdatamodel);
		log.info(queryTestDataMongo(mongoconndata,getdatamodel));
//		getTestData("localhost", "autoconfigreader","test123#","configdb","configuration");
	}
}
