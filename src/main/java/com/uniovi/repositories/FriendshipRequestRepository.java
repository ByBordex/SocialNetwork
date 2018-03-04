package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entites.FriendshipRequest;

@Repository
public interface FriendshipRequestRepository extends CrudRepository<FriendshipRequest, Long> {

	@Query("SELECT r FROM TFriendshipRequest WHERE receiver_id = ?1 ")
	Page<FriendshipRequest> findRequestToUser(Pageable pageable, Long receiverID);

}
