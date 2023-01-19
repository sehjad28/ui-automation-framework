package com.cinch.testautomation.ui.security;

import com.cinch.testautomation.ui.utilities.LoadLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.zaproxy.clientapi.core.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Properties;
import org.zaproxy.clientapi.gen.Reports;

public class ZapScan {

    static Properties prop = new Properties();

    public static Properties getProperties(String source) {
        try {
            InputStream inputStream = ZapScan.class.getClassLoader().getResourceAsStream(source);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + source + "' not found in the classpath");
            }
            return prop;
        }
        catch (Exception ex) {
            // Should never happen.
            throw new IllegalArgumentException("Failed to parse [" + source + "] into Properties", ex);
        }
    }

    /*    private final static String ZAP_PROXYHOST = "localhost";
    private final static int ZAP_PROXYPORT = 8080;
    private final static String ZAP_APIKEY = "cchs-63636873";*/

    static String ZAP_PROXYHOST = getProperties("zap.properties").getProperty("ZAP_PROXYHOST");
    static String ZAP_PROXYPORT = String.valueOf(getProperties("zap.properties").getProperty("ZAP_PROXYPORT"));
    static String ZAP_APIKEY = getProperties("zap.properties").getProperty("ZAP_APIKEY");

    static Logger log = Logger.getLogger(LoadLogger.class);

    static ClientApi api = new ClientApi(ZAP_PROXYHOST, Integer.parseInt(ZAP_PROXYPORT), ZAP_APIKEY);

    public static Proxy createZapProxyConfigurationForWebDriver() {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(ZAP_PROXYHOST + ":" + ZAP_PROXYPORT);
        proxy.setSslProxy(ZAP_PROXYHOST + ":" + ZAP_PROXYPORT);
        return proxy;
    }

    public static void passiveScan(String URL) {
        // Start spidering the target
        log.info("Spidering target : " + URL);

//            ApiResponse resp = api.spider.scan(URL, null, null, null, null);
        String scanID;
        int numberOfRecords;

        // The scan returns a scan id to support concurrent scanning
//            scanID = ((ApiResponseElement) resp).getValue();
        // Poll the status until it completes
        try {
            // TODO : explore the app (Spider, etc) before using the Passive Scan API, Refer the explore section for details
            // Loop until the passive scan has finished
            do {
                Thread.sleep(2000);
                api.pscan.enableAllScanners();
                api.pscan.recordsToScan();
                numberOfRecords = Integer.parseInt(((ApiResponseElement) api.pscan.recordsToScan()).getValue());
                System.out.println("Number of records left for scanning : " + numberOfRecords);
            } while (numberOfRecords != 0);

            log.info("Passive scanning completed");
            // If required post process the spider results
            // List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();

            // TODO: Explore the Application more with Ajax Spider or Start scanning the application for vulnerabilities

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void generateZapReport() throws Exception {
        /*ApiResponse result = api.reports.generate("Title", "traditional-html", null, "ZAP Report",
                null, null, null, null, null,
                "zapreport.html", null, null, null);*/
        byte[] bytes = api.core.htmlreport();
        // storing the bytes in to html report.
        String str = new String(bytes, StandardCharsets.UTF_8);
        File newTextFile = new File("zap_security_report.html");
        try (FileWriter fw = new FileWriter(newTextFile)) {
            fw.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZapScan zp = new ZapScan();
        String ZAP_PROXYHOST = zp.getProperties("zap.properties").getProperty("ZAP_PROXYHOST");
        String ZAP_PROXYPORT = String.valueOf(zp.getProperties("zap.properties").getProperty("ZAP_PROXYPORT"));
        String ZAP_APIKEY = zp.getProperties("zap.properties").getProperty("ZAP_APIKEY");
        System.out.println(ZAP_PROXYHOST);
        System.out.println(ZAP_PROXYPORT);
        System.out.println(ZAP_APIKEY);
    }
}