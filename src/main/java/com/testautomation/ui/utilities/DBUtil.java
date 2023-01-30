/*
 * @author Sampad Rout
 * (C) Copyright 2020 by Cinch Home Services, Inc.
 */

package com.testautomation.ui.utilities;

import org.testng.SkipException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    String url = "jdbc:postgresql://localhost:5432/";
    String dbDriver = "org.postgresql.Driver";

    String userName = "postgres";
    String password = "Pass2020!";

    private static Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    /**
     * Retrieve the db records
     * @param db_type type of the postgres database, it should be either qatestdata or accelerateqa. Case insensitive.
     * @param db_name database name to connect
     */
    public void connect(String db_type, String db_name) {
        try {
            Class.forName(dbDriver).newInstance();
            if (db_type.toLowerCase().equals("qatestdata")) {
                conn = DriverManager.getConnection(url + db_name, userName, password);
            } else if (db_type.toLowerCase().equals("accelerateqa")) {
                conn = DriverManager.getConnection(url + db_name, userName, password);
            } else {
                System.out.println("db_type is missing");
            }
        } catch (Exception e) {
            System.out.println("Could not establish connection");
            e.printStackTrace();
            //Assert.fail("Could not establish connection");
            throw new SkipException("Could not establish connection");
        }
    }

    public void disConnect(){
        try {
            if(rs!=null)
                rs.close();

            if(pstmt!=null)
                pstmt.close();

            if((conn!=null) && (!conn.isClosed()))
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve the db records
     * @param query the query to be executed
     */
    public ResultSet execute_query_postgres(String query) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<Map<String, String>> getTestDataList(String query) throws SQLException {
        List<Map<String, String>> testDataList = new ArrayList<>();

        try {
            ResultSet resultSet = execute_query_postgres(query);

            ResultSetMetaData meta = resultSet.getMetaData();
            while (resultSet.next()) {
                Map map = new HashMap();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    String key = meta.getColumnName(i);
                    String value = resultSet.getString(key);
                    map.put(key, value);
                }
                testDataList.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  testDataList;
    }
}