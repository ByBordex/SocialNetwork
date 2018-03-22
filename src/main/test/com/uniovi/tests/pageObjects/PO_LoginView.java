package main.test.com.uniovi.tests.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView {

	public static void fillForm(WebDriver driver, String dnip, String passwordp) {
		// Rellenamos el nombre de usuario
		WebElement dni = driver.findElement(By.name("username")); 
		dni.click();
		dni.clear();
		dni.sendKeys(dnip);
		// Rellenamos la contrasenia de usuario
		WebElement name = driver.findElement(By.name("password"));
		name.click();
		name.clear();
		name.sendKeys(passwordp);
		// Pulsar el boton de logear.
		WebElement boton = driver.findElement(By.className("btn"));
		boton.click();

	}

}
