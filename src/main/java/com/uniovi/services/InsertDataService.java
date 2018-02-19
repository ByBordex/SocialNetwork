package com.uniovi.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entites.User;
import com.uniovi.repositories.UsersRepository;

public class InsertDataService {

	@Autowired
	UsersRepository usersRepo;
	
	public void setup() {
		initUsers();
	}
	
	private void initUsers() {
		
		User u1 = new User(1L,"juanb@correo.com","Juan Buenga","password");
		User u2 = new User(2L,"alba@correo.com","Alba Buenga","password");
		User u3 = new User(3L,"pedrod@correo.com","Pedro Damas","password");
		User u4 = new User(4L,"peElena@correo.com","Elena Perez","password");
		User u5 = new User(5L,"foSergio@correo.com","Sergio Fondon","password");
		User u6 = new User(6L,"pahe@correo.com","Paula Hevia","password");
		
		usersRepo.save( u1 );
		usersRepo.save( u2 );
		usersRepo.save( u3 );
		usersRepo.save( u4 );
		usersRepo.save( u5 );
		usersRepo.save( u6 );
		
	}
	
}
