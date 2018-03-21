package com.uniovi.tests.pageObjects;

import org.openqa.selenium.WebDriver;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_HomeView extends PO_NavView {

	static public void checkWelcome(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		switch (language) {
		case 0:
			SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message", PO_Properties.SPANISH),
					getTimeout());
			break;
		case 1:
			SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("welcome.message", PO_Properties.ENGLISH),
					getTimeout());
			break;
		}

	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
		// Cambiamos a segundo idioma
		PO_HomeView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_HomeView.checkWelcome(driver, locale2);
		// Volvemos a Español.
		PO_HomeView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_HomeView.checkWelcome(driver, locale1);
	}

}
