package main.test.com.uniovi.tests;

import java.util.Calendar;
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
import main.test.com.uniovi.tests.pageObjects.PO_PrivateView;
import main.test.com.uniovi.tests.pageObjects.PO_Properties;
import main.test.com.uniovi.tests.pageObjects.PO_RegisterView;
import main.test.com.uniovi.tests.pageObjects.PO_View;
import main.test.com.uniovi.tests.utils.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FacelogTests {
	static String PathFirefox = "C:\\Users\\Soondra\\Documents\\Uni\\3º\\2do_trimestre\\SDI\\Firefox46.win\\FirefoxPortable.exe";

	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}
	
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	} 

	@BeforeClass
	static public void begin() {
	}

	@AfterClass
	static public void end() {
		driver.quit();
	}

	// 1.1 [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void H01_RegVal() { 
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary"); //
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba3@mail.com", "Josefo Perez", "77777", "77777");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Perfil");
	}

	// 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de contraseña invalida)
	@Test
	public void H01_RegInval() { 
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "1@mail.com", "Josefo Perez", "77777", "77777");
		PO_View.getP();
		// Comprobamos el error de DNI repetido.
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba2@mail.com", "Jos", "77777", "77777");
		// Comprobamos el error de Nombre corto.
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

	// 2.1 [InVal] Inicio de sesión con datos válidos desde el ROl de Usuario, 1@mail.com, 123456
	@Test
	public void H02_InVal() { 
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en la aplicación)
	@Test
	public void H02_InInVal() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noexiste@mail.com", "123456");
		// Comprobamos que entramos en la pagina privada de profesor
		PO_View.checkElement(driver, "text", "La combinacion usuario/password no coincide");
	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void H03_LisUsrVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la lista de usuarios
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobar que se nos muestran usuarios
		PO_View.checkElement(driver, "free", "//td[contains(text(), '@')]");
	}

	// 3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado al listado de usuarios
	// desde un usuario en sesión. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void H03_ListUsrInVal() {
		// Modificamos la URL a mano para ir a la lista de usuarios
		String URLinvalid = URL + "/user/list";
		driver.navigate().to( URLinvalid );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que nos redirige en la página de login
		PO_View.checkElement(driver, "text", "Identificate");
	}

	// 4.1 [BusUsrVal] Realizar una búsqueda valida en el listado de usuarios desde un usuario en sesión.
	@Test 
	public void H04_BusUsrVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 

		// Buscamos los usuarios que contengan la cadena 'mar'
		PO_PrivateView.searchUser(driver, "mar");
		// Comprobamos que tiene los usuarios que concuerdan
		// María Rodriguez
		PO_View.checkElement(driver, "text", "3@mail.com");
		// Marta Almonte
		PO_View.checkElement(driver, "text", "4@mail.com");
		// María Buenaga
		PO_View.checkElement(driver, "text", "7@mail.com");
		// Marina Lopez
		PO_View.checkElement(driver, "text", "9@mail.com");
		// Cristina Martinez
		PO_View.checkElement(driver, "text", "11@mail.com");
		// Comprobamos que no tiene otros usuarios
		// Lucas Nuñez
		SeleniumUtils.textoNoPresentePagina(driver, "2@mail.com");
		// Pelayo Valdés
		SeleniumUtils.textoNoPresentePagina(driver, "5@mail.com");

		// Buscamos los usuarios que contengan la cadena '@'
		PO_PrivateView.searchUser(driver, "@");
		// Comprobamos que tiene los usuarios que concuerdan
		// Lucas Nuñez
		PO_View.checkElement(driver, "text", "2@mail.com");
		// Marta Rodriguez
		PO_View.checkElement(driver, "text", "3@mail.com");
		// María Almonte
		PO_View.checkElement(driver, "text", "4@mail.com");
		// Pelayo Valdés
		PO_View.checkElement(driver, "text", "5@mail.com");

		// Buscamos los usuarios que contengan la cadena 'í'
		PO_PrivateView.searchUser(driver, "í");
		// Comprobamos que tiene los usuarios que concuerdan
		// Cristina Martinez
		PO_View.checkElement(driver, "text", "11@mail.com");
		// Comprobamos que no tiene otros usuarios
		// Lucas Nuñez
		SeleniumUtils.textoNoPresentePagina(driver, "2@mail.com");

		// Buscamos los usuarios que contengan la cadena 'dw'
		PO_PrivateView.searchUser(driver, "dw");
		// Comprobamos que tiene los usuarios que concuerdan
		// Edward Núñez
		PO_View.checkElement(driver, "text", "6@mail.com");
		// Comprobamos que no tiene otros usuarios
		// Lucas Nuñez
		SeleniumUtils.textoNoPresentePagina(driver, "2@mail.com");
		// TODO check tildes y ñ
	}

	// 4.2 [BusUsrInVal] Intento de acceso con URL a la búsqueda de usuarios desde un usuario no
	// identificado. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void H04_BusUsrInVal() {
		// Modificamos la URL a mano para ir a la lista de usuarios
		String URLinvalid = URL + "/user/list";
		driver.navigate().to( URLinvalid );
		// Comprobamos que nos redirige en la página de login
		PO_View.checkElement(driver, "text", "Identificate");
	}

	// 5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void H04_InvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la lista de usuarios.
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobamos que existe el botón de amistad para el usuario2.
		List<WebElement> elementos = PO_View.checkElement(driver, "id", "sendButton2");

		// Pulsamos el botón para enviar la solicitud.
		elementos.get(0).click();
	}

	// 5.2 [InvInVal] Enviar una invitación de amistad a un usuario al que ya le habíamos invitado la invitación
	// previamente. No debería dejarnos enviar la invitación, se podría ocultar el botón de enviar invitación o
	// notificar que ya había sido enviada previamente.
	@Test
	public void H05_InvInVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la lista de usuarios.
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobamos que existe el botón de amistad para el usuario2.
		PO_View.checkElement(driver, "id", "sendButton2");

		// Comprobamos que el botón ahora tiene btn-warning representando "Pendiente" y
		// está deshabilitado
		PO_View.checkElement(driver, "free",
				"//button[contains(text(), 'Pendiente') "
						+ "		and contains(@class, 'btn btn-warning')"
						+ "		 and @disabled]");
	}

	// 6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la comprobación con una lista
	// que al menos tenga una invitación recibida.
	@Test
	public void H06_LisInvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar peticiones de amistad
		PO_PrivateView.listFriendshipRequests( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que estamos en la pantalla de solicitudes de amistad
		PO_View.checkElement(driver, "text", "Los siguientes usuarios te han pedido ser amigos:");
		// Comprobamos que ha llegado la petición de amistad
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_View.checkElement(driver, "free", "//td[contains(text(), '1@mail.com')]");
	}

	// 7.1 [AcepInvVal] Aceptar una invitación recibida.
	@Test
	public void H07_AcepInvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar peticiones de amistad
		PO_PrivateView.listFriendshipRequests( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que estamos en la pantalla de solicitudes de amistad
		PO_View.checkElement(driver, "text", "Los siguientes usuarios te han pedido ser amigos:");
		// Comprobamos que ha llegado la petición de amistad
		SeleniumUtils.esperarSegundos(driver, 1);
		PO_View.checkElement(driver, "free", "//td[contains( text(), '1@mail.com' )]");
		// Aceptamos la petición de amistad
		PO_PrivateView.clickOption(driver, "Aceptar", "class", "btn btn-primary");
	}

	// 8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación con una lista que al menos
	// tenga un amigo.
	@Test 
	public void H08_ListAmiVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar amigos
		PO_PrivateView.listFriends( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la lista de amigos
		PO_View.checkElement(driver, "text", "Amigos");
		// Comprobamos que el usuario 1@mail.com es amigo nuestro
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Comprobamos que no tenemos mas amigos 
		SeleniumUtils.textoNoPresentePagina(driver, "2@mail.com");
		SeleniumUtils.textoNoPresentePagina(driver, "3@mail.com");
		SeleniumUtils.textoNoPresentePagina(driver, "4@mail.com");
	}

	// 9.1 [PubVal] Crear una publicación con datos válidos.
	@Test
	public void H09_PubVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de crear post
		PO_PrivateView.createPost( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que estamos en el creador de posts
		PO_View.checkElement(driver, "text", "Crear publicacion");
		// Creamos una publicación con título "Test" y contenido "Esto es parte del test"
		PO_PrivateView.fillFormAddPost(driver, "Test", "Esto es parte del test");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que nos redericciona a las publicaciones y aparece la que hemos creado
		PO_View.checkElement(driver, "text", "Mis publicaciones");
		PO_View.checkElement(driver, "text", "Test");
	}

	// 10.1 [LisPubVal] Acceso al listado de publicaciones desde un usuario en sesión.
	@Test
	public void H10_ListPubVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar mis posts
		PO_PrivateView.listPosts( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que accedimos a las publicaciones
		PO_View.checkElement(driver, "text", "Mis publicaciones");
		Calendar calendar = Calendar.getInstance();
		String date = String.valueOf(calendar.get( Calendar.DATE )) 
				+ "/" + String.valueOf(calendar.get( Calendar.MONTH )) 
				+ "/" + String.valueOf(calendar.get( Calendar.YEAR ));
		PO_View.checkElement(driver, "text", date);
	}

	// 11.1 [LisPubAmiVal] Listar las publicaciones de un usuario amigo
	@Test 
	public void H11_ListPubAmiVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		// Seleccionamos la opción de listar amigos
		PO_PrivateView.listFriends( driver );
		SeleniumUtils.esperarSegundos(driver, 2); 
		// Comprobamos que entramos en la lista de amigos
		PO_View.checkElement(driver, "text", "Amigos");
		// TODO 
	}

	// 11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las publicaciones de un usuario que
	// no sea amigo del usuario identificado en sesión.
	@Test
	public void H11_LisPubAmiInVal() {
		//TODO
	}

	// 12.1 [PubFot1Val] Crear una publicación con datos válidos y una foto adjunta.
	@Test
	public void H12_PubFot1Val() {
		//TODO
	}

	// 12.1 [PubFot2Val] Crear una publicación con datos válidos y sin una foto adjunta.
	@Test
	public void H12_PubFot2Val() {
		//TODO
	}

	// 13.1 [AdInVal] Inicio de sesión como administrador con datos válidos.
	@Test
	public void H13_AdInVal() {
		//TODO
	}

	// 13.2 [AdInInVal] Inicio de sesión como administrador con datos inválidos (usar los datos de un usuario
	// que no tenga perfil administrador).
	@Test
	public void H13_AdInInVal() {
		//TODO
	}

	// 14.1 [AdLisUsrVal] Desde un usuario identificado en sesión como administrador listar a todos los
	// usuarios de la aplicación.
	@Test
	public void H14_AdListUsrVal() {
		//TODO
	}

	// 15.1 [AdBorUsrVal] Desde un usuario identificado en sesión como administrador eliminar un usuario
	// existente en la aplicación.
	@Test
	public void H15_AdBorUsrVal() {
		//TODO
	}

	// 15.2 [AdBorUsrInVal] Intento de acceso vía URL al borrado de un usuario existente en la aplicación.
	// Debe utilizarse un usuario identificado en sesión pero que no tenga perfil de administrador.
	@Test
	public void H15_AdBorUsrInVal() {
		//TODO
	}

}
