package com.uniovi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entites.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		usersRepository.findAll().forEach(users::add);
		return users;
	}

	public User getMark(Long id) {
		return usersRepository.findOne(id);
	}

	public void addMark(User user) {
		 usersRepository.save(user);
	}

	public void deleteMark(Long id) {
		usersRepository.delete(id);
	}
}
