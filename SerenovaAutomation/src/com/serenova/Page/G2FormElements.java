/**
 * Class contains the Web page element's locations belongs to Group 1 URL forms.  
 * @author Shrikant Jaiswal
 **/
package com.serenova.Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class G2FormElements {
	private static WebDriverWait wait;

	public static WebElement getObjFirstNameTextBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[2]/input"))));
		return driver.findElement(By.xpath("//p[2]/input"));
	}

	public static WebElement getObjLastNameTextBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[3]/input"))));
		return driver.findElement(By.xpath("//p[3]/input"));
	}

	public static WebElement getObjEmailTextBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[4]/input"))));
		return driver.findElement(By.xpath("//p[4]/input"));
	}

	public static WebElement getObjCompanyTextBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[5]/input"))));
		return driver.findElement(By.xpath("//p[5]/input"));
	}

	public static WebElement getObjPhoneTextBox(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[6]/input"))));
		return driver.findElement(By.xpath("//p[6]/input"));
	}

	public static WebElement getObjSelectCountry(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[7]/select"))));
		return driver.findElement(By.xpath("//p[7]/select/option[2]"));
	}

	public static WebElement getObjSelectState(WebDriver driver) {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//p[8]/select"))));
		return driver.findElement(By.xpath("//p[8]/select/option[57]"));
	}
}
