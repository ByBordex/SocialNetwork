package com.uniovi.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entites.FriendshipRequest;

@Repository
public interface FriendshipRequestRepository extends CrudRepository<FriendshipRequest, Long> {

}
