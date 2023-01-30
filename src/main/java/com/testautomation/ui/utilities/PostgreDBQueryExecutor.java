/*
 * @author Vasanth Soundararajan
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.utilities;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SuppressWarnings("serial")
public class PostgreDBQueryExecutor { // extends AbstractDBQueryExecutor {
    protected static final Logger logger = LoggerFactory.getLogger(PostgreDBQueryExecutor.class);
    protected static String dbhost = null;
    protected static String dbport = null;
    protected static String dbname = null;
    protected static String dbusername = null;
    protected static String dbpassword = null;
    protected static String driver = "org.postgresql.Driver";
    protected static String dburl;
    protected static JdbcTemplate jdbctemplate;// = new JdbcTemplate();
    protected static DriverManagerDataSource dataSource = new DriverManagerDataSource();
//              public static PostgreDBQueryExecutor pgresqe = new PostgreDBQueryExecutor();

    //              @Override
    private static void getDBConnection() {
        try {
/*            dbhost = getPropertyValue("pgsqlhost");
            dbport = getPropertyValue("pgsqlport");
            dbname = getPropertyValue("pgsqldbname");
            dbusername = getPropertyValue("pgsqldbusername");
            dbpassword = getPropertyValue("pgsqldbpassword");*/
            dburl = "jdbc:postgresql://" + dbhost + ":" + dbport + "/" + dbname;
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(dburl);
            System.out.println("DBURL: " + dburl);
            logger.info("init jdbc template: {}\n", dburl);
            dataSource.setUsername(dbusername);
            dataSource.setPassword(dbpassword);
            jdbctemplate = new JdbcTemplate(dataSource);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    private static void getDBConnection(String dbhost, String dbport, String dbname, String dbusername, String dbpassword) {
        try {
            dburl = "jdbc:postgresql://" + dbhost + ":" + dbport + "/" + dbname;
            System.out.println("DB URL:" + dburl);
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(dburl);
            dataSource.setUsername(dbusername);
            dataSource.setPassword(dbpassword);
            jdbctemplate = new JdbcTemplate(dataSource);
            logger.info("init jdbc template: {} \n", dburl);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
    }

    /*
                    PostgreDBQueryExecutor() {
                                    getDBConnection();
                    }
    */
    PostgreDBQueryExecutor(String query, String dbhost, String dbport, String dbname, String dbusername, String dbpassword) {
        getDBConnection(dbhost, dbport, dbname, dbusername, dbpassword);
    }

    public static Object getSingleValueFromTable(String query) {
//                              logger.info("init jdbc template: {}", jdbctemplate.queryForObject(query, Object.class));

        logger.info("Query: {}", query);
        getDBConnection();
        return jdbctemplate.queryForObject(query, Object.class);
    }

    public static Map<String, Object> getSingleRowFromTable(String query) {
        getDBConnection();
        return jdbctemplate.queryForMap(query);
    }

    public static List<Map<String, Object>> getMultipleRowsFromTable(String query) {
        getDBConnection();
        return jdbctemplate.queryForList(query);
    }

    public static Object getSingleValueFromTable(String query, String dbhost, String dbport, String dbname, String dbusername, String dbpassword) {
        logger.info("Query: {}", query);
        getDBConnection(dbhost, dbport, dbname, dbusername, dbpassword);
        return jdbctemplate.queryForObject(query, Object.class);
    }

    public static Map<String, Object> getSingleRowFromTable(String query, String dbhost, String dbport, String dbname,
                                                            String dbusername, String dbpassword) {
        getDBConnection(dbhost, dbport, dbname, dbusername, dbpassword);
        return jdbctemplate.queryForMap(query);
    }

    public static List<Map<String, Object>> getMultipleRowsFromTable(String query, String dbhost, String dbport, String dbname, String dbusername, String dbpassword) {
        getDBConnection(dbhost, dbport, dbname, dbusername, dbpassword);
        return jdbctemplate.queryForList(query);
    }

    public static void main(String[] args) {
        String query = "select max(customer_account_id) from customer.customer_account";
//        PostgreDBQueryExecutor pgdb = new PostgreDBQueryExecutor(query, "db.qa.accelerate.cinchhs.com", "5432","accelerateqa", "qaadmin", "uCNW9N76");
        System.out.println("Max:" + PostgreDBQueryExecutor.getSingleValueFromTable(query, "db.qa.accelerate.cinchhs.com", "5432", "accelerateqa", "accel_ms_qa", "l9tAcVCwdrJ5e"));
//        System.out.println(PostgreDBQueryExecutor.getSingleValueFromTable(query));

    }

}