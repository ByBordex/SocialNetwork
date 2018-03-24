package com.uniovi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.uniovi.entites.Post;
import com.uniovi.entites.User;

public interface PostRepository extends CrudRepository<Post,Long>{

	@Query("SELECT u.posts FROM User u WHERE u = ?1")
	List<Post> findPostsFromUser(User author);
	
	@Query("SELECT count(p) FROM Post p WHERE p.user = ?1")
	int countPostsFromUser(User author);
	
}
