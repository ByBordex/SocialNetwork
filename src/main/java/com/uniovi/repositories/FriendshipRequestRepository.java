package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.uniovi.entites.FriendshipRequest;
import com.uniovi.entites.User;

public interface FriendshipRequestRepository extends CrudRepository<FriendshipRequest, Long> {

	@Query("SELECT r FROM FriendshipRequest r WHERE r.receiver = ?1 AND r.accepted = false ORDER BY r.id ASC")
	Page<FriendshipRequest> findPendingRequestToUser(Pageable pageable, User receiver);

	@Query("SELECT r FROM FriendshipRequest r WHERE r.sender = ?1 AND r.accepted = false ORDER BY r.id ASC")
	List<FriendshipRequest> findPendingRequestFromUser(User sender);
	
	@Transactional
	@Modifying
	@Query("UPDATE FriendshipRequest SET accepted = true WHERE id=?1 AND receiver = ?2")
	void acceptRequest(Long id, User receiver);
	
	@Query("SELECT DISTINCT u FROM User u WHERE u IN "
			+ "	(SELECT DISTINCT r.sender FROM FriendshipRequest r WHERE r.receiver = ?1 AND r.accepted = true)"
			+ " OR u IN"
			+ " (SELECT DISTINCT r.receiver FROM FriendshipRequest r WHERE r.sender = ?1 AND r.accepted = true)")
	Page<User> getFriends(Pageable pageable, User user);
	
	@Query("SELECT DISTINCT u FROM User u WHERE u IN "
			+ "	(SELECT DISTINCT r.sender FROM FriendshipRequest r WHERE r.receiver = ?1 AND r.accepted = true)"
			+ " OR u IN"
			+ " (SELECT DISTINCT r.receiver FROM FriendshipRequest r WHERE r.sender = ?1 AND r.accepted = true)")
	List<User> getFriends(User user);
	
	@Query("SELECT DISTINCT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?2) OR" 
			+ " LOWER(u.email) LIKE LOWER(?2))"
			+ " AND u IN"
			+ " (SELECT DISTINCT r.sender FROM FriendshipRequest r WHERE r.receiver = ?1 AND r.accepted = true)"
			+ " OR u IN"
			+ " (SELECT DISTINCT r.receiver FROM FriendshipRequest r WHERE r.sender = ?1 AND r.accepted = true)")
	Page<User> searchFriendsByNameOrEmail(Pageable pageable, User user, String searchText);
	
}
