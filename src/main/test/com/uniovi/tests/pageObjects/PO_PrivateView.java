package main.test.com.uniovi.tests.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import main.test.com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView{

	static public void searchUser(WebDriver driver, String user) {
		SeleniumUtils.esperarSegundos(driver, 5);
		WebElement text = driver.findElement(By.name("searchText"));
		text.click();
		text.clear();
		text.sendKeys(user);
		By boton = By.id("searchBtn");
		driver.findElement(boton).click();
	}

	static public void acceptRequest(WebDriver driver, String username)
	{
		SeleniumUtils.esperarSegundos(driver, 5);
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//tr[td[contains(text(), '"+username+"')]]"
				+ "/td/button");
		elementos.get(0).click();
	}

	static public void listUsers(WebDriver driver) {
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'user/list' )]");
		elementos.get(0).click();
	}

	static public void listFriendshipRequests(WebDriver driver) {
		// Pinchamos en la opción de menu de Amigos - Peticiones de amistad:
		// li[contains(@id, 'friends-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar peticiones de amistad:
		// a[contains(@href, 'friendshipRequest/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'friendshipRequest/list' )]");
		elementos.get(1).click();
	}

	static public void listFriends(WebDriver driver) {
		// Pinchamos en la opción de menu de Amigos - Ver mis amigos:
		// li[contains(@id, 'friends-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'friends-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de listar amigos:
		// a[contains(@href, 'friendshipRequest/listFriends')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'friendshipRequest/listFriends' )]");
		elementos.get(0).click();
	}

	static public void createPost(WebDriver driver) {
		// Pinchamos en la opción de menu de Publicaicones - Crear publicacion:
		// li[contains(@id, 'posts-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de crear publicacion:
		// a[contains(@href, 'posts/post')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'posts/post' )]");
		elementos.get(1).click();
	}

	static public void fillFormAddPost(WebDriver driver, String titulop,
			String contenidop)
	{
		// Esperamos 5 segundo a que carge el DOM 
		SeleniumUtils.esperarSegundos(driver, 5);
		// Rellenemos el campo de descripción
		WebElement titulo = driver.findElement( By.name( "title" ) );
		titulo.clear();
		titulo.sendKeys( titulop );
		WebElement contenido = driver.findElement( By.name( "content" ) );
		contenido.click();
		contenido.clear();
		contenido.sendKeys( contenidop );
		// Le damos al botón de enviar
		By boton = By.className( "btn btn-primary" );
		driver.findElement( boton ).click();
	}
	
	static public void fillFormAddPost(WebDriver driver, String titulop,
			String contenidop, String imagep)
	{
		// Esperamos 5 segundo a que carge el DOM 
		SeleniumUtils.esperarSegundos(driver, 5);
		// Rellenemos el campo de descripción
		WebElement titulo = driver.findElement( By.name( "title" ) );
		titulo.clear();
		titulo.sendKeys( titulop );
		WebElement contenido = driver.findElement( By.name( "content" ) );
		contenido.click();
		contenido.clear();
		contenido.sendKeys( contenidop );
		String path = System.getProperty( "user.dir" ) + "/src/resources/static/img/posts/" + imagep;
		WebElement photo = driver.findElement( By.name("photo") );
		photo.sendKeys( path );
		// Le damos al botón de enviar
		By boton = By.className( "btn btn-primary" );
		driver.findElement( boton ).click();
	}

	static public void listPosts(WebDriver driver) {
		// Pinchamos en la opción de menu de Publicaicones - Ver mis publicaciones:
		// li[contains(@id, 'posts-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'posts-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de lista mis publicaciones:
		// a[contains(@href, 'posts/post')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'posts/list' )]");
		elementos.get(0).click();
	}

	static public void listUsersAdmin(WebDriver driver) {
		// Pinchamos en la opción de menu de Panel de administrador - Gestion de usuarios:
		// li[contains(@id, 'admin-menu')]/a
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'admin-menu')]/a");
		elementos.get(0).click();
		// Esperamos a aparezca la opción de lista los usuarios:
		// a[contains(@href, 'admin/user/list')]
		elementos = PO_View.checkElement(driver, "free", "//a[contains( @href, 'admin/user/list' )]");
		elementos.get(0).click();
	}

}