/**
 * Class contains the automation logic to fill the forms belongs to group 1 and 2.
 *   
 * @author Shrikant Jaiswal
 **/
package com.serenova.script;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import com.serenova.Page.FormElements;
import com.serenova.Page.G2FormElements;
import com.serenova.utils.Mailer;
import com.serenova.utils.ProcessStatus;
import com.serenova.utils.Utils;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

public class SerenovaScript {

	private static WebDriver driver;
	private static BrowserMobProxy proxy;
	private static String parentWindowHandle;

	public static final String USERNAME = "brandonmcpeak1";
	public static final String AUTOMATE_KEY = "6sFopSbKXj8fJGPLznCz";
	static int port = 0;

	public static void executeScript(String page, String executeURL, String expectedResultURL) throws InterruptedException, Exception {
		driver = getDriver(page);
		SessionId sessionID = ((RemoteWebDriver) driver).getSessionId();
		
		// Maximize window
		driver.manage().window().maximize();
		System.out.println("Executing URL :" + executeURL);
		// open page
		Utils.openPage(executeURL, driver);
		// switch frame
		if (!executeURL.contains("success")) {
			parentWindowHandle = driver.getWindowHandle();
			driver.switchTo().frame(FormElements.getObjFormFrame());
		}
		
		// fill form

		// insert firstname
		FormElements.getObjFirstNameTextBox(driver).sendKeys("BTEST");

		// insert last name
		String lastname = (page + "_" + Utils.getDate());
		FormElements.getObjLastNameTextBox(driver).sendKeys(lastname);

		// insert email
		FormElements.getObjEmailTextBox(driver).sendKeys(lastname + "@test.co");
		// insert company
		FormElements.getObjCompanyTextBox(driver).sendKeys("ACCENTURE SERVICES PRIVATE LIMITED");
		Thread.sleep(5000);
		
		// insert phone number
		FormElements.getObjPhoneTextBox(driver).sendKeys("5555555555");

		// set country
		FormElements.getObjSelectCountry(driver).click();
		
		// select state
		FormElements.getObjSelectState(driver).click();
		if (!page.contains("G2")) {
			// insert of agent
			FormElements.getObjofAgentTextBox(driver).sendKeys("1");
		}

		// press submit
		System.out.println("Submit");
		if (!executeURL.contains("success")) {
			FormElements.getObjSubmitButton(driver).click();
			Thread.sleep(10000);
			try {
				driver.switchTo().window(parentWindowHandle);
			} catch (Exception e) {
				System.err.println("Facing issue on window switching");
			}
		} else {
			FormElements.getObjDownloadNowButton(driver).click();
		}
		Thread.sleep(10000);
		String resultURL = driver.getCurrentUrl();
		if (verifyResultURL(expectedResultURL, resultURL)) {
			markStatus("passed", "", sessionID);
		} else {
			markStatus("failed", "", sessionID);
		}
		Thread.sleep(5000);
		driver.quit();
	}

	public static void executeG2Script(String page, String executeURL, String expectedResultURL) throws InterruptedException, Exception {
		driver = getDriver(page);
		SessionId sessionID = ((RemoteWebDriver) driver).getSessionId();
		
		// Maximize window
		driver.manage().window().maximize();
		System.out.println("Executing URL :" + executeURL);
		
		// open page
		Utils.openPage(executeURL, driver);
		
		// switch frame
		driver.switchTo().frame(FormElements.getObjFormFrame());
		
		// fill form
		
		// insert firstname
		G2FormElements.getObjFirstNameTextBox(driver).sendKeys("BTEST");
		
		// insert last name
		String lastname = (page + "_" + Utils.getDate());
		G2FormElements.getObjLastNameTextBox(driver).sendKeys(lastname);
		
		// insert email
		G2FormElements.getObjEmailTextBox(driver).sendKeys(lastname + "@test.co");
		
		// insert company
		G2FormElements.getObjCompanyTextBox(driver).sendKeys("ACCENTURE SERVICES PRIVATE LIMITED");
		Thread.sleep(5000);
		
		// insert phone number
		G2FormElements.getObjPhoneTextBox(driver).sendKeys("5555555555");
		
		// set country
		G2FormElements.getObjSelectCountry(driver).click();
		Thread.sleep(20000);
		
		// set city
		G2FormElements.getObjSelectState(driver).click();
		
		// press submit
		FormElements.getObjSubmitButton(driver).click();
		Thread.sleep(10000);
		driver.switchTo().defaultContent();
		Thread.sleep(10000);
		String resultURL = driver.getCurrentUrl();
		if (verifyResultURL(expectedResultURL, resultURL)) {
			markStatus("passed", "", sessionID);
		} else {
			markStatus("failed", "", sessionID);
		}
		driver.quit();
	}

	private static WebDriver getDriver(String page) throws Exception {

		String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("browser", "IE");
		// set browser version
		caps.setCapability("browser_version", "11.0");
		// set OS
		caps.setCapability("os", "Windows");
		// OS version
		caps.setCapability("os_version", "10");
		caps.setCapability("acceptSslCerts", "true");
		caps.setCapability("browserstack.debug", "true");
		caps.setCapability("name", page);
		caps.setCapability("browserstack.local", true);
		// caps.setCapability("browserstack.networkLogs",true);

		// check is browserstack exe is alive
		if (!ProcessStatus.isProcessIsAlive()) {
			proxy = new BrowserMobProxyServer();
			proxy.start();
			port = proxy.getPort();
			String cmd = "BrowserStackLocal.exe --key " + AUTOMATE_KEY
					+ " --local-proxy-host localhost --local-proxy-port " + port + " --force-local --force-proxy";
			System.out.println(cmd);
			Runtime.getRuntime().exec(cmd);
			Thread.sleep(10000);
			System.out.println(proxy.getPort());
			HashSet blackListURL = new HashSet<String>();
			blackListURL.add("https?://.*(google-analytics.com)+.*");
			proxy.setBlacklist(blackListURL);
		}
		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.get("https://go.pardot.com/l/311851/2017-04-19/4ltx");
		return driver;
	}

	private static boolean verifyResultURL(String expectedResultURL, String resultURL) {
		System.out.println("RESULT   URL : " + resultURL);
		System.out.println("EXPECTED URL : " + expectedResultURL);
		Mailer.emailResultMEssage = Mailer.emailResultMEssage + expectedResultURL + " --- " + expectedResultURL;
		if (expectedResultURL.compareToIgnoreCase(resultURL) == 0) {
			Mailer.emailResultMEssage = Mailer.emailResultMEssage + " --- PASS\n";
			return true;
		}
		Mailer.emailResultMEssage = Mailer.emailResultMEssage + " - FAIL\n";
		return false;
	}

	public static void markStatus(String status, String reason, SessionId sessionID)
			throws URISyntaxException, UnsupportedEncodingException, IOException {
		String url = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@www.browserstack.com/automate/sessions/" + sessionID
				+ ".json";
		URI uri = new URI(url);
		HttpPut putRequest = new HttpPut(uri);
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add((new BasicNameValuePair("status", status)));
		nameValuePairs.add((new BasicNameValuePair("reason", reason)));
		putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpClientBuilder.create().build().execute(putRequest);
	}
}