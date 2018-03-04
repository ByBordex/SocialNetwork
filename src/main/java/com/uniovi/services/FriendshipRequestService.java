package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public void sendRequest(String senderMail, Long receiverID){
		User sender =  usersRepository.findByEmail(senderMail);
		User receiver = usersRepository.findOne(receiverID);
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
