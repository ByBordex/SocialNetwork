package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entites.FriendshipRequest;
import com.uniovi.entites.User;
import com.uniovi.repositories.FriendshipRequestRepository;

@Service
public class FriendshipRequestService {
	
	@Autowired
	FriendshipRequestRepository friendshipRequestRepo;
	
	@Autowired
	UsersService userService;
	
	public void sendRequest(Long senderId, Long receiverId){
		User sender =  userService.getUser(senderId);
		User receiver = userService.getUser(receiverId);
		FriendshipRequest fr = new FriendshipRequest( sender, receiver );
		
		friendshipRequestRepo.save( fr );
	}
	
	/** Accept a given FriendshipRequest ID
	 * 
	 * @param id
	 */
	public void acceptRequest(Long id) {
		//TODO
	}
	
	/** Return the list of request received for a given userID.
	 * 
	 * @param The id of the receiver of the request.
	 * @return The list a request an user has.
	 */
	public List<FriendshipRequest> getRequestToUser(Long receiverId)
	{
		//TODO
		return null;
	}
	
}
