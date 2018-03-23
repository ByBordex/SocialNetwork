package main.test.com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
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
	
	static String PathFirefox = "C:\\Users\\Soondra\\Documents\\Uni\\3º\\2do_trimestre\\SDI\\SpringBoot\\SocialNetwork\\Firefox46.win\\"
			+ "FirefoxPortable.exe";
	// static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
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
	public void H01_1_RegVal() { 
		// Vamos al formulario de registro
		driver.findElement( By.id( "btnSignup" ) ).click();
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "prueba@mail.com", "Josefo Perez", "77777", "77777");
		// Comprobamos que entramos en la sección privada
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "prueba@mail.com");
	}

	// 1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de contraseña invalida)
	@Test
	public void H01_2_RegInval() { 
		// Vamos al formulario de registro
		driver.findElement( By.id( "btnSignup" ) ).click();
		assertTrue( driver.getCurrentUrl().equals( "localhost:8090/signup" ) );
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
		// Comprobamos que la contraseña es corta
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario
		PO_RegisterView.fillForm(driver, "99999990B", "Josefo Perez", "7777777", "1111111");
		// Comprobamos que el apellido es corto
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// 2.1 [InVal] Inicio de sesión con datos válidos desde el ROl de Usuario, 1@mail.com, 123456
	@Test
	public void H02_1_InVal() { 
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en la aplicación)
	@Test
	public void H02_2_InInVal() {
		// Vamos al formulario de logueo.
		driver.findElements( By.id( "btnLogin" ) ).get(0).click();
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "noexiste@mail.com", "123456");
		// Comprobamos que seguimos en la página de login
		PO_View.checkElement(driver, "text", "La combinacion usuario/password no coincide");
	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void H03_1_LisUsrVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		// Comprobamos que entramos en la lista de usuarios
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes:");
		// Comprobar que se nos muestran usuarios
		PO_View.checkElement(driver, "free", "//td[contains(text(), '@')]");
	}

	// 3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado al listado de usuarios
	// desde un usuario en sesión. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void H03_2_ListUsrInVal() {
		// Modificamos la URL a mano para ir a la lista de usuarios
		String URLinvalid = URL + "/user/list";
		driver.navigate().to( URLinvalid );
		// Comprobamos que nos redirige en la página de login
		PO_View.checkElement(driver, "text", "Identificate");
	}

	// 4.1 [BusUsrVal] Realizar una búsqueda valida en el listado de usuarios desde un usuario en sesión.
	@Test 
	public void H04_1_BusUsrVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );

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
	public void H04_2_BusUsrInVal() {
		// Modificamos la URL a mano para ir a la lista de usuarios
		String URLinvalid = URL + "/user/list";
		driver.navigate().to( URLinvalid );
		// Comprobamos que nos redirige en la página de login
		PO_View.checkElement(driver, "text", "Identificate");
	}

	// 5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida.
	@Test
	public void H05_1_InvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
		// Comprobamos que entramos en la lista de usuarios.
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes");
		// Comprobamos que existe el botón de amistad para el usuario2 y lo clicamos
		PO_View.checkElement(driver, "id", "sendButton2").get( 0 ).click();
		// Comrpobamos que ya no podemos darle click
		PO_View.checkElement(driver, "free",
				"//button[contains(text(), 'Pendiente') "
						+ "		and contains(@class, 'btn btn-warning')"
						+ "		 and @disabled]");
		
	}

	// 5.2 [InvInVal] Enviar una invitación de amistad a un usuario al que ya le habíamos invitado la invitación
	// previamente. No debería dejarnos enviar la invitación, se podría ocultar el botón de enviar invitación o
	// notificar que ya había sido enviada previamente.
	@Test
	public void H05_2_InvInVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar usuarios
		PO_PrivateView.listUsers( driver );
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
	public void H06_1_LisInvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "2@mail.com");
		// Seleccionamos la opción de listar peticiones de amistad
		PO_PrivateView.listFriendshipRequests( driver );
		// Comprobamos que estamos en la pantalla de solicitudes de amistad
		PO_View.checkElement(driver, "text", "Los siguientes usuarios te han pedido ser amigos:");
		// Comprobamos que ha llegado la petición de amistad
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//tr//td");
		assertTrue( elementos.size() == 3 );
	}

	// 7.1 [AcepInvVal] Aceptar una invitación recibida.
	@Test
	public void H07_1_AcepInvVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "2@mail.com");
		// Seleccionamos la opción de listar peticiones de amistad
		PO_PrivateView.listFriendshipRequests( driver );
		// Comprobamos que estamos en la pantalla de solicitudes de amistad
		PO_View.checkElement(driver, "text", "Los siguientes usuarios te han pedido ser amigos:");
		// Comprobamos que ha llegado la petición de amistad
		PO_View.checkElement(driver, "free", "//td[contains( text(), '1@mail.com' )]");
		// Aceptamos la petición de amistad
		PO_PrivateView.acceptRequest(driver, "1@mail.com");
		SeleniumUtils.textoPresentePagina(driver, "Aceptar");
	}

	// 8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación con una lista que al menos
	// tenga un amigo.
	@Test 
	public void H08_1_ListAmiVal() {
		// Iniciamos sesión
		PO_NavView.clickConectarCon(driver, "2");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "2@mail.com");
		// Seleccionamos la opción de listar amigos
		PO_PrivateView.listFriends( driver );
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
	public void H09_1_PubVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de crear post
		PO_PrivateView.createPost( driver );
		// Comprobamos que estamos en el creador de posts
		PO_View.checkElement(driver, "text", "Crear publicacion");
		// Creamos una publicación con título "Test" y contenido "Esto es parte del test"
		PO_PrivateView.fillFormAddPost(driver, "Test", "Esto es parte del test");
		// Comprobamos que nos redericciona a las publicaciones y aparece la que hemos creado
		PO_View.checkElement(driver, "text", "Mis publicaciones");
	}

	// 10.1 [LisPubVal] Acceso al listado de publicaciones desde un usuario en sesión.
	@Test
	public void H10_1_ListPubVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar mis posts
		PO_PrivateView.listPosts( driver );
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
	public void H11_1_ListPubAmiVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de listar amigos
		PO_PrivateView.listFriends( driver );
		// Comprobamos que entramos en la lista de amigos
		PO_View.checkElement(driver, "text", "Amigos");
		// Pinchamos en 'Ver perfil'
		PO_HomeView.clickOption(driver, "Ver perfil", "id", "postButton3");
		// Comprobamos que estamos en el perfil de nuestro amigo
		PO_View.checkElement(driver, "text", "Perfil de");
		PO_View.checkElement(driver, "text", "Lucas Nuñez");
	}

	// 11.2 [LisPubAmiInVal] Utilizando un acceso vía URL tratar de listar las publicaciones de un usuario que
	// no sea amigo del usuario identificado en sesión.
	@Test
	public void H11_2_LisPubAmiInVal() {
		// Modificamos la URL a mano para ir a la lista de usuarios
		String URLinvalid = URL + "/friendshipRequest/listFriends/3";
		driver.navigate().to( URLinvalid );
		// Comprobamos que nos redirige a la página de nuestros amigos
		// y que el usuario 3 no es nuestro amigo
		PO_View.checkElement(driver, "text", "Amigos");
		SeleniumUtils.textoNoPresentePagina(driver, "3@mail.com");
	}

	// 12.1 [PubFot1Val] Crear una publicación con datos válidos y una foto adjunta.
	@Test
	public void H12_1_PubFot1Val() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de crear post
		PO_PrivateView.createPost( driver );
		// Comprobamos que estamos en el creador de posts
		PO_View.checkElement(driver, "text", "Crear publicacion");
		// Creamos una publicación con título "Test" y contenido "Esto es parte del test"
		PO_PrivateView.fillFormAddPost(driver, "Test", "Esto es parte del test de imagen", "0.png");
		// Comprobamos que nos redericciona a las publicaciones y aparece la que hemos creado
		PO_View.checkElement(driver, "text", "Mis publicaciones");
		PO_View.checkElement(driver, "text", "Test");
		PO_View.checkElement(driver, "text", "Esto es parte del test de imagen");
	}

	// 12.1 [PubFot2Val] Crear una publicación con datos válidos y sin una foto adjunta.
	@Test
	public void H12_1_PubFot2Val() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Seleccionamos la opción de crear post
		PO_PrivateView.createPost( driver );
		// Comprobamos que estamos en el creador de posts
		PO_View.checkElement(driver, "text", "Crear publicacion");
		// Creamos una publicación con título "Test" y contenido "Esto es parte del test"
		PO_PrivateView.fillFormAddPost(driver, "Test", "Esto es parte del test");
		// Comprobamos que nos redericciona a las publicaciones y aparece la que hemos creado
		PO_View.checkElement(driver, "text", "Mis publicaciones");
		PO_View.checkElement(driver, "text", "Test");
	}

	// 13.1 [AdInVal] Inicio de sesión como administrador con datos válidos.
	@Test
	public void H13_1_AdInVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "admin");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "admin@mail.com");
	}

	// 13.2 [AdInInVal] Inicio de sesión como administrador con datos inválidos (usar los datos de un usuario
	// que no tenga perfil administrador).
	@Test
	public void H13_2_AdInInVal() {
		// Vamos al formulario de logueo por URL
		String URLinvalid = URL + "/admin/login";
		driver.navigate().to( URLinvalid );
		// Rellenamos el formulario con datos válidos de un usuario no admin
		PO_LoginView.fillForm(driver, "1@mail.com", "123456");
		// Comprobamos que entramos en la pagina privada de profesor
		PO_View.checkElement(driver, "text", "La combinacion usuario/password no coincide");
	}

	// 14.1 [AdLisUsrVal] Desde un usuario identificado en sesión como administrador listar a todos los
	// usuarios de la aplicación.
	@Test
	public void H14_1_AdListUsrVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "admin");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "admin@mail.com");
		// Vamos a la gestión de usuarios
		PO_PrivateView.listUsersAdmin( driver );
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes:");
		PO_View.checkElement(driver, "text", "Eliminar");
		PO_View.checkElement(driver, "text", "1@mail.com");
		PO_View.checkElement(driver, "text", "15@mail.com");
		PO_View.checkElement(driver, "text", "prueba@mail.com");
	}

	// 15.1 [AdBorUsrVal] Desde un usuario identificado en sesión como administrador eliminar un usuario
	// existente en la aplicación.
	@Test
	public void H15_1_AdBorUsrVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "admin");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "admin@mail.com");
		// Vamos a la gestión de usuarios
		PO_PrivateView.listUsersAdmin( driver );
		// Comprobamos que estamos en la lista de usuarios
		PO_View.checkElement(driver, "text", "Los usuarios que actualmente figuran en el sistema son los siguientes:");
		PO_View.checkElement(driver, "text", "Eliminar");
		PO_View.checkElement(driver, "text", "prueba@mail.com");
		// Eliminamos el usuario prueba@mail.com
		PO_HomeView.clickOption(driver, "Eliminar", "id", "removeButton16");
		// Comprobamos que el usuario ya no esta en la lista
		SeleniumUtils.textoNoPresentePagina(driver, "prueba@mail.com");
	}

	// 15.2 [AdBorUsrInVal] Intento de acceso vía URL al borrado de un usuario existente en la aplicación.
	// Debe utilizarse un usuario identificado en sesión pero que no tenga perfil de administrador.
	@Test
	public void H15_2_AdBorUsrInVal() {
		// Iniciamos sesion
		PO_NavView.clickConectarCon(driver, "1");
		// Comprobamos que entramos en la pagina privada 
		PO_View.checkElement(driver, "text", "Perfil");
		PO_View.checkElement(driver, "text", "1@mail.com");
		// Modificamos la URL a mano para ir a la lista de borrado de usuarios
		String URLinvalid = URL + "/admin/user/list";
		driver.navigate().to( URLinvalid );
		// Comprobamos que nos redirecciona a nuestra página de inicio
		//TODO Access is denied
	}

}
