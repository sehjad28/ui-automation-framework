package com.testautomation.ui.thirdparty;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.testautomation.ui.base.ThirdPartyTestExecutor;
import com.testautomation.ui.enums.TestResult;


public class CrossBrowserTestingExecutor extends ThirdPartyTestExecutor
{
	private String apiUrl = "crossbrowsertesting.com/api/v3/selenium";
	private String sessionId=null;
	static String username = "vsoundararajan@cinchhs.com";
	static String authkey = "ua97f7de54ada357";
	public static CrossBrowserTestingExecutor cbt = new CrossBrowserTestingExecutor();
	


	
	public void setDescription(String desc) {
		String url = "https://" + apiUrl + "/" + this.sessionId;
		String payload = "{\"action\": \"set_description\", \"description\": \"" + desc + "\"}";
		makeRequest("PUT", url,payload);
	}

	@Override
	public URL get3rdPartyURL(String username, String password) {
		URL CBTUrl = null;
		try {
			if (username.contains("@")) { username = username.replace("@", "%40"); }
			CBTUrl = new URL("http://" + username + ":" + password + "@hub.crossbrowsertesting.com:80/wd/hub");
		} catch (MalformedURLException e) { System.out.println("Invalid HUB URL"); }
		return CBTUrl;
	}

	@Override
	public void setSessionId(String sessionId) { this.sessionId = sessionId; }

	@Override
	public void takeSnapshot() {
		if (this.sessionId != null) {
			String url = "https://" + apiUrl + "/" + this.sessionId + "/snapshots";
			String payload = "{\"selenium_test_id\": \"" + this.sessionId + "\"}";
			makeRequest("POST",url,payload);
		}
	}
	
	@Override
	public void setResult(TestResult result) {
		String url = "https://" + apiUrl + "/" + this.sessionId;
		String payload = "{\"action\": \"set_score\", \"score\": \"" + result + "\"}";
		makeRequest("PUT", url,payload);
		
	}

	private void makeRequest(String requestMethod, String apiUrl, String payload) {
		URL url;
		String auth = "";
        if (username != null && authkey != null) {
            auth = "Basic " + Base64.encodeBase64String((username+":" + authkey).getBytes());
        }
        try {
            url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", auth);
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
            osw.write(payload);
            osw.flush();
            osw.close();
            conn.getResponseMessage();
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
	}
	
	public static void main(String[] args) {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("name", "Test CBT");
		caps.setCapability("browserName", "Internet Explorer");
		caps.setCapability("version", "10"); // If this cap isn't specified, it will just get the latest one
		caps.setCapability("platform", "Windows 7 64-Bit");
		caps.setCapability("screenResolution", "1366x768");
		caps.setCapability("record_video", "true");
		caps.setCapability("record_network", "false");
		
		RemoteWebDriver driver = null;
		TestResult score = null;

		try {
			driver = new RemoteWebDriver(cbt.get3rdPartyURL(username, authkey), caps);
			cbt.setSessionId(driver.getSessionId().toString());
			driver.get("https://www.crossbrowsertesting.com");
			cbt.takeSnapshot();
			cbt.setDescription("CBT Test");

			// depending on whether the value of the title is correct,
			// set the score to pass or fail via CBT's API
			if (driver.getTitle().equals("Cross Browser Testing Tool: 2050+ Real Browsers & Devices"))
				score = TestResult.PASS;
			 else
				score = TestResult.FAIL;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (driver != null)
				driver.quit();
			cbt.setResult(score);
		}
	}

	@Override
	protected String executeTestsOnCBT(String browser, String browserVersion, String platformName, String methodName) {
		// TODO Auto-generated method stub
		return null;
	}


}
