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
	private UsersRepository marksRepository;

	public List<User> getMarks() {
		List<User> marks = new ArrayList<User>();
		marksRepository.findAll().forEach(marks::add);
		return marks;
	}

	public User getMark(Long id) {
		return marksRepository.findOne(id);
	}

	public void addMark(User mark) {
		 marksRepository.save(mark);
	}

	public void deleteMark(Long id) {
		marksRepository.delete(id);
	}
}
