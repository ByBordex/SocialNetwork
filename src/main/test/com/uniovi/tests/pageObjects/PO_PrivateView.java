package com.uniovi.tests.pageObjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.utils.SeleniumUtils;

public class PO_PrivateView extends PO_NavView{

	static public void searchUser(WebDriver driver, String user) {
		WebElement text = driver.findElement( By.name( "searchText" ) );
		text.click();
		text.clear();
		text.sendKeys(user);
		By boton = By.id("searchBtn");
		driver.findElement(boton).click();
		// Comprobamos que entramos a la página correcta
		String expectedUrl = "http://localhost:8090/user/list?searchText=" + user;
		assertTrue( expectedUrl.equals( driver.getCurrentUrl() ) );
	}

	static public void acceptRequest(WebDriver driver, String username)
	{
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//tr[td[contains(text(), '"+username+"')]]"
				+ "/td/button");
		elementos.get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/friendshipRequest/list" ) );
	}

	static public void listUsers(WebDriver driver) {
		// Pinchamos en la opción de menu de Gestionar usuarios:
		// li[contains(@id, 'users-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'users-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de listar usuarios:
		// a[contains(@href, '/user/list')]
		driver.findElements( By.xpath( "//a[contains( @href, '/user/list' )]" )).get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/user/list" ) );
	}

	static public void listFriendshipRequests(WebDriver driver) {
		// Pinchamos en la opción de menu de Amigos - Peticiones de amistad:
		// li[contains(@id, 'friends-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'friends-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de listar peticiones de amistad:
		// a[contains(@href, 'friendshipRequest/list')]
		driver.findElements( By.xpath( "//a[contains( @href, '/friendshipRequest/list' )]" )).get(1).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/friendshipRequest/list" ) );
	}

	static public void listFriends(WebDriver driver) {
		// Pinchamos en la opción de menu de Amigos - Ver mis amigos:
		// li[contains(@id, 'friends-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'friends-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de listar amigos:
		// a[contains(@href, 'friendshipRequest/listFriends')]
		driver.findElements( By.xpath( "//a[contains( @href, '/friendshipRequest/listFriends' )]" )).get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/friendshipRequest/listFriends" ) );
	}

	static public void createPost(WebDriver driver) {
		// Pinchamos en la opción de menu de Publicaicones - Crear publicacion:
		// li[contains(@id, 'posts-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'posts-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de crear publicacion:
		// a[contains(@href, 'posts/post')]
		driver.findElements( By.xpath( "//li/a[contains( @href, '/posts/post' )]" )).get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/posts/post" ) );
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
		PO_View.checkElement(driver, "id", "submit").get( 0 ).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/posts/list" ) );
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
		driver.findElement( By.id( "submit" ) ).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/posts/list" ) );
	}

	static public void listPosts(WebDriver driver) {
		// Pinchamos en la opción de menu de Publicaicones - Ver mis publicaciones:
		// li[contains(@id, 'posts-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'posts-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de lista mis publicaciones:
		// a[contains(@href, 'posts/post')]
		driver.findElements( By.xpath( "//li/a[contains( @href, '/posts/list' )]" )).get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/posts/list" ) );
	}

	static public void listUsersAdmin(WebDriver driver) {
		// Pinchamos en la opción de menu de Panel de administrador - Gestion de usuarios:
		// li[contains(@id, 'admin-menu')]/a
		driver.findElements( By.xpath( "//li[contains(@id, 'admin-menu')]/a" )).get(0).click();
		// Esperamos a aparezca la opción de lista los usuarios:
		// a[contains(@href, 'admin/user/list')]
		driver.findElements( By.xpath( "//li/a[contains( @href, '/admin/user/list' )]" )).get(0).click();
		// Comprobamos que entramos a la página correcta
		assertTrue( driver.getCurrentUrl().equals( "http://localhost:8090/admin/user/list" ) );
	}

}