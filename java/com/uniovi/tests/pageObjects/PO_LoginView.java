package com.uniovi.tests.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView {

	public static void fillForm(WebDriver driver, String dnip, String passwordp) {

		WebElement dni = driver.findElement(By.name("username"));
		dni.click();
		dni.clear();
		dni.sendKeys(dnip);
		WebElement name = driver.findElement(By.name("password"));
		name.click();
		name.clear();
		name.sendKeys(passwordp);
		// Pulsar el boton de Alta.
		WebElement boton = driver.findElement(By.className("btn"));
		boton.click();

	}

	public static void checkRoleProfesor(WebDriver driver) {
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
		elementos.get(0).click();
		PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
	}

}
