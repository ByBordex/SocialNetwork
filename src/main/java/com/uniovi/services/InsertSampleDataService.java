package com.uniovi.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entites.User;

//@Service
public class InsertSampleDataService {
	@Autowired
	private UsersService usersService;

	@PostConstruct
	public void init() { 
		
		User user1 = new User("1@mail.com", "Pedro Díaz");
		user1.setPassword("123456");
		User user2 = new User("2@mail.com", "Lucas Núñez");
		user2.setPassword("123456");
		User user3 = new User("3@mail.com", "María Rodríguez");
		user3.setPassword("123456");
		User user4 = new User("4@mail.com", "Marta Almonte");
		user4.setPassword("123456");
		User user5 = new User("5@mail.com", "Pelayo Valdes");
		user5.setPassword("123456");
		User user6 = new User("6@mail.com", "Edward Núñez");
		user6.setPassword("123456");
		User user7 = new User("7@mail.com", "Maria Buenaga");
		user7.setPassword("123456");
		User user8 = new User("8@mail.com", "Álvaro Gayo");
		user8.setPassword("123456");
		User user9 = new User("9@mail.com", "Marina López");
		user9.setPassword("123456");
		User user10 = new User("10@mail.com", "Enol Álvarez");
		user10.setPassword("123456");
		User user11 = new User("11@mail.com", "Cristina Martínez");
		user11.setPassword("123456");
		User user12 = new User("12@mail.com", "Alejandra Pérez");
		user12.setPassword("123456");

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
	}
}