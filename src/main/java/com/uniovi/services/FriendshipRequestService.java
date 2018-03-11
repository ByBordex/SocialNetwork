package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.uniovi.entites.FriendshipRequest;
import com.uniovi.entites.User;
import com.uniovi.repositories.FriendshipRequestRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class FriendshipRequestService {
	
	@Autowired
	FriendshipRequestRepository friendshipRequestRepo;
	
	@Autowired
	UsersRepository usersRepository;
	
	public void sendRequest(User sender, Long receiverID){
		User receiver = usersRepository.findOne(receiverID);
		FriendshipRequest fr = new FriendshipRequest( sender, receiver );
		friendshipRequestRepo.save( fr );
	}
	
	public void acceptRequest(Long id, User receiver) {
		friendshipRequestRepo.acceptRequest(id, receiver);	
	}
	
	public Page<FriendshipRequest> getPendingRequestToUser(Pageable pageable, User receiver) {
		return friendshipRequestRepo.findPendingRequestToUser(pageable, receiver);
	}

	public Set<User> getUsersFromSendedRequest(User activeUser) {
		Set<User> usersRequestedFriendship = new HashSet<User>();
		for (FriendshipRequest fr : friendshipRequestRepo.findPendingRequestFromUser(activeUser)) {
			usersRequestedFriendship.add(fr.getReceiver());
		}
		return usersRequestedFriendship;
	}
	
	public Page<User> getFriends(Pageable pageable, User user) {
		return friendshipRequestRepo.getFriends(pageable, user);
	}
	
	public Page<User> searchFriendsByNameOrEmail(Pageable pageable, User user, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = friendshipRequestRepo.searchFriendsByNameOrEmail(pageable, user, searchText);
		return users;
	}
	
}
