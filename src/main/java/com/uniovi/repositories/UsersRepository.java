package com.uniovi.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entites.User;

public interface UsersRepository extends CrudRepository<User, Long> {

	User findByEmail(String email);

	Page<User> findAll(Pageable pageable);

	@Query("SELECT u FROM User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.email) LIKE LOWER(?1))")
	Page<User> searchByNameOrEmail(Pageable pageable, String searchtext);
	
}
