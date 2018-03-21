package com.uniovi.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

	public void sendRequest(User sender, Long receiverID) {
		User receiver = usersRepository.findOne(receiverID);
		FriendshipRequest fr = new FriendshipRequest(sender, receiver);
		friendshipRequestRepo.save(fr);
	}

	public void acceptRequest(Long id, User receiver) {
		friendshipRequestRepo.acceptRequest(id, receiver);
	}

	public Page<FriendshipRequest> getPendingRequestToUser(Pageable pageable, User receiver) {
		return friendshipRequestRepo.findPendingRequestToUser(pageable, receiver);
	}

	/**
	 * @param activeUser
	 * @return The users which received a request from activeUser
	 */
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

	public List<User> getFriends(User user) {
		return friendshipRequestRepo.getFriends(user);
	}

	public Page<User> searchFriendsByNameOrEmail(Pageable pageable, User user, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		searchText = "%" + searchText + "%";
		users = friendshipRequestRepo.searchFriendsByNameOrEmail(pageable, user, searchText);
		return users;
	}

	public Set<User> getFriendsInList(User user, List<User> listOfUsers) {
		Set<User> fr = new HashSet<User>();
		List<User> friends = getFriends(user);
		for (User u : listOfUsers) {
			if (friends.contains(u))
				fr.add(u);
		}
		return fr;
	}

	/**
	 * @param activeUser
	 * @return The users which received a request from activeUser
	 */
	public Set<User> getUserSendedRequesToUser(User activeUser, List<User> content) {
		List<User> usersRequestUserFriendship = friendshipRequestRepo.findPendingRequestToUser(activeUser).stream()
				.map(x -> x.getSender()).collect(Collectors.toList());
		
		Set<User> users = new HashSet<User>();
		for (User u : content) {
			if (usersRequestUserFriendship.contains(u)) {
				users.add(u);
			}
		}
		return users;
	}

}
