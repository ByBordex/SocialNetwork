package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageObjects.PO_HomeView;
import com.uniovi.tests.pageObjects.PO_LoginView;
import com.uniovi.tests.pageObjects.PO_Properties;
import com.uniovi.tests.pageObjects.PO_RegisterView;
import com.uniovi.tests.pageObjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FacelogTest {

	static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";

	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navegara al home
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	} // Antes de la primera prueba

	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01. Acceder a la página principal

	@Test
	public void PR01() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR02. OPción de navegación. Pinchar en el enlace Registro en la página
	// home

	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	// PR03. OPción de navegación. Pinchar en el enlace Identificate en la página
	// home

	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	// PR04. OPción de navegación. Cambio de idioma de Español a Ingles y vuelta
	// Español

	@Test
	public void PR04() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		SeleniumUtils.esperarSegundos(driver, 2);
	}

	// PR05. Prueba del formulario de registro. registro con datos correctos

	@Test
	public void PR05() { // Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary"); //
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "1@correo.com", "Josefo Manuel", "77777", "77777");
		SeleniumUtils.esperarSegundos(driver, 2); // Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text","Autenticado como");
	}

	// PR06. Prueba del formulario de registro. DNI repetido en la BD, Nombre corto,
	// .... pagination
	@Test
	public void PR06() { // Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "1@mail.com", "Josefo Perez", "77777", "77777");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "99999990B", "Je",  "77777", "77777");
		// COmprobamos el error de Nombre corto.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.																							// formulario
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo","77", "77");
		// Comprobamos que el apellido es corto
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo", "7777777", "1111111");
		// Comprobamos que el apellido es corto
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PRN. Loguearse con exito desde el ROl de Usuario, 99999990D, 123456

	@Test
	public void PR07() { // Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999990A", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Notas del usuario");
	}

	// PRN. Loguearse con exito desde el ROl de profesor, 99999993D, 123456
	@Test
	public void PR08() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999993D", "123456");
		// COmprobamos que entramos en la pagina privada de profesor
		PO_LoginView.checkRoleProfesor(driver);

	}

	// PRN. Loguearse con exito desde el ROl de admin, 99999988F, 123456
	@Test
	public void PR09() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "99999988F", "123456");
		// COmprobamos que entramos en la pagina privada de profesor
		PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");

	}

}