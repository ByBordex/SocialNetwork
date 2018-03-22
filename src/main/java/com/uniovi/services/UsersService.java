package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uniovi.entites.FriendshipRequest;
import com.uniovi.entites.User;
import com.uniovi.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Page<User> getUsers(Pageable pageable) {
		return usersRepository.findAll(pageable);
	}
	public List<User> getUsers() {
		List<User> list = new ArrayList<User>();
		usersRepository.findAll().forEach(list::add);
		return list;
	}

	public User getUser(Long id) {
		return usersRepository.findOne(id);
	}

	public Set<User> getUsersFriendshipRequiredBy(User user) {
		Set<User> usersRequestedFriendship = new HashSet<User>();
		for (FriendshipRequest fr : user.getRequestSended()) {
			usersRequestedFriendship.add(fr.getReceiver());
		}
		return usersRequestedFriendship;
	}

	public Set<User> getUsersFriendshipRequiredInList(User user, List<User> possibleRequested) {
		Set<User> requestedUsers = new HashSet<User>();
		Set<User> possibleRequestedUsers = getUsersFriendshipRequiredBy(user);
		for (User possibleUser : possibleRequestedUsers) {
			if (possibleRequested.contains(possibleUser)) {
				requestedUsers.add(possibleUser);
			}

		}
		return requestedUsers;
	}

	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		usersRepository.save(user);
	}

	public User getUserByEmail(String dni) {
		return usersRepository.findByEmail(dni);
	}

	public void deleteUser(Long id) {
		usersRepository.delete(id);
	}

	public Page<User> searchByNameOrEmail(Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = usersRepository.searchByNameOrEmail(pageable, searchText);
		return users;
	}

	public void removeUser(User user) {
		usersRepository.delete(user);
	}

}