package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uniovi.entites.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
	
	Page<User> findAll(Pageable pageable);
}
