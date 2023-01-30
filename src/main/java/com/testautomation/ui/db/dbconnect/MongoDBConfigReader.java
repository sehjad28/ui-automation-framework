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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@SuppressWarnings({ "deprecation", "unchecked", "resource" })
public class MongoDBConfigReader {

	static Logger log = Logger.getLogger(LoadLogger.class);

	public static void loadPropertiesFromMongoDB(MongoConnectionData mongoconndata,GetDataFromMongoModel gdmodel) {
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
//			getQuery.append("tcName", gdmodel.getTcName());
			System.out.println("Query: " + getQuery);
			log.info("Query: " + getQuery);
			DBCursor cursor = collection.find(getQuery);

			while (cursor.hasNext()) {
				String configdata = cursor.next().toMap().get("configData").toString();
				ObjectMapper mapper = new ObjectMapper();

				Map<String, String> configDataMap = mapper.readValue(configdata, Map.class);
				System.out.println(configDataMap);
				log.info(configDataMap);

				Properties props = new Properties();
				InputStream targetStream = new ByteArrayInputStream(configDataMap.toString().getBytes());
				props.load(targetStream);
				props.list(System.out);
			}
		} catch (Exception ex) {
			System.out.println(ex);
			log.error(ex);
		}
	}

	public static void main(String[] args) {
		MongoConnectionData mongoconndata = new MongoConnectionData();
		mongoconndata.setHostname("automationdb.qa.accelerate.cinchhs.com");
		mongoconndata.setUsername("acceladmin");
		mongoconndata.setPassword("l3xr06lqhtzKc");

//		mongoconndata.setDBName("configdb");
//		mongoconndata.setCollectionname("configuration");
		
		GetDataFromMongoModel getdatamodel = new GetDataFromMongoModel();
		getdatamodel.setDbName("testdatadb");
		getdatamodel.setCollectionName("testdata");
		getdatamodel.setAppName("msAccelCustomer1");
		getdatamodel.setEnvName("qa");
		getdatamodel.setTcName("abc");
		getdatamodel.setDatatype("testcaseData");
		loadPropertiesFromMongoDB(mongoconndata, getdatamodel);
	}
}
