package com.uniovi.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TPosts")
public class Post {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String content;
	
	@ManyToOne
	private User user;
	
	public Post() {}
	
	public Post(String content, User user) {
		super();
		this.content = content;
		this.user = user;
	}

	public Long getId() { return id; }
	
	public String getContent() { return content; }
	
	public void setContent(String content) { this.content = content; }
	
	public User getUser() { return user; }
	
	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "Post [id=" + id + ", content=" + content + ", user=" + user + "]";
	}
	
}
