package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entites.Post;
import com.uniovi.entites.User;

@Service
public class InsertSampleDataService {
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private FriendshipRequestService friendshipRequestService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private RolesService roles;


	@PostConstruct
	public void init() { 
		
		User user1 = new User("1@mail.com", "Pedro Díaz");
		user1.setPassword("123456");
		user1.setRole( roles.getRole(0) );
		User user2 = new User("2@mail.com", "Lucas Núñez");
		user2.setRole( roles.getRole(0) );
		user2.setPassword("123456");
		User user3 = new User("3@mail.com", "María Rodríguez");
		user3.setRole( roles.getRole(0) );
		user3.setPassword("123456");
		User user4 = new User("4@mail.com", "Marta Almonte");
		user4.setRole( roles.getRole(0) );
		user4.setPassword("123456");
		User user5 = new User("5@mail.com", "Pelayo Valdes");
		user5.setRole( roles.getRole(0) );
		user5.setPassword("123456");
		User user6 = new User("6@mail.com", "Edward Núñez");
		user6.setRole( roles.getRole(0) );
		user6.setPassword("123456");
		User user7 = new User("7@mail.com", "Maria Buenaga");
		user7.setRole( roles.getRole(0) );
		user7.setPassword("123456");
		User user8 = new User("8@mail.com", "Álvaro Gayo");
		user8.setRole( roles.getRole(0) );
		user8.setPassword("123456");
		User user9 = new User("9@mail.com", "Marina López");
		user9.setRole( roles.getRole(0) );
		user9.setPassword("123456");
		User user10 = new User("10@mail.com", "Enol Álvarez");
		user10.setRole( roles.getRole(0) );
		user10.setPassword("123456");
		User user11 = new User("11@mail.com", "Cristina Martínez");
		user11.setRole( roles.getRole(0) );
		user11.setPassword("123456");
		User user12 = new User("12@mail.com", "Alejandra Pérez");
		user12.setRole( roles.getRole(0) );
		user12.setPassword("123456");
		User user13 = new User("13@mail.com", "Carlos Vega");
		user13.setRole( roles.getRole(0) );
		user13.setPassword("123456");
		User user14 = new User("14@mail.com", "Caridad Meana");
		user14.setRole( roles.getRole(0) );
		user14.setPassword("123456");
		User user15 = new User("15@mail.com", "Sergio Miranda");
		user15.setRole( roles.getRole(0) );
		user15.setPassword("123456");
		
		User admin = new User("admin@mail.com", "Sergio Miranda");
		admin.setRole( roles.getRole(1) );
		admin.setPassword("123456");
		
		
		usersService.addUser(user1);
		usersService.addUser(user2);
		usersService.addUser(user3);
		usersService.addUser(user4);
		usersService.addUser(user5);
		usersService.addUser(user6);
		usersService.addUser(user7);
		usersService.addUser(user8);
		usersService.addUser(user9);
		usersService.addUser(user10);
		usersService.addUser(user11);
		usersService.addUser(user12);
		usersService.addUser(user13);
		usersService.addUser(user14);
		usersService.addUser(user15);
		usersService.addUser(admin);
		
		friendshipRequestService.sendRequest(user2, user1.getId());
		friendshipRequestService.sendRequest(user3, user1.getId());
		friendshipRequestService.sendRequest(user4, user1.getId());
		friendshipRequestService.sendRequest(user5, user1.getId());
		friendshipRequestService.sendRequest(user6, user1.getId());
		friendshipRequestService.sendRequest(user7, user1.getId());
		friendshipRequestService.sendRequest(user8, user1.getId());
		
		Post post1 = new Post("Post 1", "Esto es una prueba");
		post1.setUser(user1);
		Post post2 = new Post("Post 2", "Esto es una prueba");
		post2.setUser(user1);
		Post post3 = new Post("Post 3", "Esto es una prueba");
		post3.setUser(user1);
		Post post4 = new Post("Post 4", "Esto es una prueba");
		post4.setUser(user1);

		postService.addPost(post1);
		postService.addPost(post2);
		postService.addPost(post3);
		postService.addPost(post4);
		
		Post post5 = new Post("Post 5", "Esto es una prueba");
		post5.setUser(user2);
		Post post6 = new Post("Post 6", "Esto es una prueba");
		post6.setUser(user2);
		Post post7 = new Post("Post 7", "Esto es una prueba");
		post7.setUser(user2);
		Post post8 = new Post("Post 8", "Esto es una prueba");
		post8.setUser(user2);
		
		postService.addPost(post5);
		postService.addPost(post6);
		postService.addPost(post7);
		postService.addPost(post8);
		
	}
}