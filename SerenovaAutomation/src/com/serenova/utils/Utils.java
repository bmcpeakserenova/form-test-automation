/**
 * Utility class to manage generalize operations.
 *   
 * @author Shrikant Jaiswal
 **/

package com.serenova.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;

public class Utils {
	public static Scanner getScanner(String filePath) throws FileNotFoundException {
		File f = new File(filePath);
		return new Scanner(f);
	}

	public static void openPage(String executeURL, WebDriver driver) {
		driver.get(executeURL);
	}

	public static String getDate() {
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd-YYYY");
		return sf.format(new Date());
	}

	public static String checkConfigFileParams(String configFile, String key) {
		BufferedReader reader = null;
		File file = null;
		String line = null;
		String val = "";

		file = new File(configFile);
		if (file.exists()) {
			try {
				try {
					reader = new BufferedReader(new FileReader(file));
					while ((line = reader.readLine()) != null) {
						if (line.trim().startsWith(key)) {
							String newline = line.substring(key.length(), line.length());
							if (newline.trim().startsWith("=")) {
								val = line.substring(line.indexOf('=') + 1, line.length());
							}
						}
					}
				} finally {
					if (null != reader) {
						reader.close();
					}
				}
			} catch (Exception ioe) {
				System.out.println(ioe.toString());
			}
		} else {
			System.out.println("Could not open Driver.config file!");
		}
		return (val.trim());
	}
}