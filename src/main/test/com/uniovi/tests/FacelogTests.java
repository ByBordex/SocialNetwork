package main.test.com.uniovi;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import main.test.com.uniovi.tests.pageObjects.PO_HomeView;
import main.test.com.uniovi.tests.pageObjects.PO_LoginView;
import main.test.com.uniovi.tests.pageObjects.PO_NavView;
import main.test.com.uniovi.tests.pageObjects.PO_Properties;
import main.test.com.uniovi.tests.pageObjects.PO_RegisterView;
import main.test.com.uniovi.tests.pageObjects.PO_View;
import main.test.com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FacelogTests {
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
	public void mainPageTest() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
	}

	// PR02. OPción de navegación. Pinchar en el enlace Registro en la página
	// home
	@Test
	public void navToRegisterTest() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	}

	// PR03. OPción de navegación. Pinchar en el enlace Identificate en la página
	// home
	@Test
	public void navToLoginTest() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	}

	// PR04. OPción de navegación. Cambio de idioma de Español a Ingles y vuelta
	// Español
	@Test
	public void changeLanguageTest() {
		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
				PO_Properties.getENGLISH());
		SeleniumUtils.esperarSegundos(driver, 2);
	}

	// PR05. Prueba del formulario de registro. registro con datos correctos
	@Test
	public void registerValidData() { // Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary"); //
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba3@mail.com", "Josefo Perez", "77777", "77777");
		SeleniumUtils.esperarSegundos(driver, 2); // Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Perfil");
	}

	// PR06. Prueba del formulario de registro. DNI repetido en la BD, Nombre corto,
	// .... pagination
	@Test
	public void registerInvalidDataTest() { // Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "1@mail.com", "Josefo Perez", "77777", "77777");
		PO_View.getP();
		// COmprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba2@mail.com", "Jos", "77777", "77777");
		// COmprobamos el error de Nombre corto.
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formurio
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo Perez", "77", "77");
		// Comprobamos que la contraseña es corto
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo Perez", "7777777", "1111111");
		// Comprobamos que el apellido es corto
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PR_H_01. Loguearse con exito desde el ROl de Usuario, 1@mail.com, 123456
	@Test
	public void loginValiTest() { // Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "1@mail.com", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
	}

	// PR_H_01. Comprobamos login erroneo
	@Test
	public void loginErrorTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noexiste@mail.com", "123456");
		// COmprobamos que entramos en la pagina privada de profesor
		PO_View.checkElement(driver, "text", "La combinacion usuario/password no coincide");
	}

	// PR_H_02. Listar usuarios
	@Test
	public void listUsersTest() {
		loginValidTest();
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		// Comprobamos que entramos en la lista de usuarios.
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobar que se nos muestran usuarios
		SeleniumUtils.esperarSegundos(driver, 1);
		elementos = PO_View.checkElement(driver, "free", "//td[contains(text(), '@')]");
	}

	@Test
	public void sendRequestTest() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "1@mail.com", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		// Comprobamos que entramos en la lista de usuarios.
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobamos que existe el botón de amistad para el usuario2.
		elementos = PO_View.checkElement(driver, "id", "sendButton2");
		
		// Pulsamos el botón para enviar la solicitud.
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Recargamos la página
		driver.navigate().to(driver.getCurrentUrl());
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que el botón ahora tiene btn-warning representando "Pendiente" y
		// está deshabilitado
		elementos = PO_View.checkElement(driver, "free",
				"//button[contains(text(), 'Pendiente') "
				+ "		and contains(@class, 'btn btn-warning')"
				+ "		 and @disabled]");
	}

	@Test
	public void receivedRequestsTest() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "1@mail.com", "123456");
		// COmprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'user/list')]");
		// Comprobamos que entramos en la lista de usuarios.
		elementos.get(0).click();
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobamos que existe el botón de amistad para el usuario2.
		elementos = PO_View.checkElement(driver, "id", "sendButton2");
		
		// Pulsamos el botón para enviar la solicitud.
		elementos.get(0).click();
		SeleniumUtils.esperarSegundos(driver, 1);
		// Recargamos la página
		driver.navigate().to(driver.getCurrentUrl());
		SeleniumUtils.esperarSegundos(driver, 1);
		// Comprobamos que el botón ahora tiene btn-warning representando "Pendiente" y
		// está deshabilitado
		elementos = PO_View.checkElement(driver, "free",
				"//button[contains(text(), 'Pendiente') "
				+ "		and contains(@class, 'btn btn-warning')"
				+ "		 and @disabled]");
		// Cerramos sesión 1@mail.com
		PO_NavView.clickDesconectar(driver);
		// Iniciamos sesión con 2@mail.com
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "2@mail.com", "123456");
		// Pinchamos en la opción de menu de Amigos - Peticiones de amistad:
		// li[contains(@id, 'users-menu')]/a
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains( text(), 'Peticiones de amistad')]");
		elementos.get(0).click();
		// Comprobamos que estamos en la pantalla de solicitudes de amistad
		PO_View.checkElement(driver, "text", "Los siguientes usuarios te han pedido ser amigos:");
		// Comprobamos que ha llegado la petición de amistad
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_View.checkElement(driver, "free", "//td[contains(text(), '1@mail.com')]");
	}
}
