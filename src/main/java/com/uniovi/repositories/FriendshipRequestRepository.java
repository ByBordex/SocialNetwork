package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entites.FriendshipRequest;
import com.uniovi.entites.User;

@Repository
public interface FriendshipRequestRepository extends CrudRepository<FriendshipRequest, Long> {

	@Query("SELECT r FROM FriendshipRequest r WHERE r.receiver = ?1 AND r.accepted = false ORDER BY r.id ASC")
	Page<FriendshipRequest> findPendingRequestToUser(Pageable pageable, User receiver);

	@Query("UPDATE FriendshipRequest SET accept = 1 WHERE id=?1 and receiver=?2")
	void acceptRequest(Long id,User receiver  );
}
