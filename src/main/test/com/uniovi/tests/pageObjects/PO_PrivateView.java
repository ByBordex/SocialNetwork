package main.test.com.uniovi.tests.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import main.test.com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView{

	static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp,
			String scorep)
	{
		//Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
		SeleniumUtils.esperarSegundos(driver, 5);
		//Seleccionamos el alumnos userOrder
		new Select (driver.findElement(By.id("user"))).selectByIndex(userOrder);
		//Rellenemos el campo de descripción
		WebElement description = driver.findElement(By.name("description"));
		description.clear();
		description.sendKeys(descriptionp);
		WebElement score = driver.findElement(By.name("score"));
		score.click();
		score.clear();
		score.sendKeys(scorep);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}

	static public void searchUser(WebDriver driver, String user) {
		SeleniumUtils.esperarSegundos(driver, 5);
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys(user);
		By boton = By.id("searchBtn");
		driver.findElement(boton).click();
	}

	static public void listUsers(WebDriver driver) {
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		elementos.get(0).click();
	}

}