package com.uniovi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entites.Post;
import com.uniovi.entites.User;
import com.uniovi.repositories.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;
	
	public Post getPost(Long id) {
		return postRepository.findOne(id);
	}
	
	public void addPost(Post post) {
		postRepository.save(post);
	}
	
	public void deletePost(Long id) {
		postRepository.delete(id);
	}
	
	public List<Post> findPostsFromUser(User author) {
		return postRepository.findPostsFromUser( author );
	}
	
	public int countPostsFromUser(User author) {
		return postRepository.countPostsFromUser( author );
	}
	
	public int getLast() {
		return (int) postRepository.count();
	}
}
