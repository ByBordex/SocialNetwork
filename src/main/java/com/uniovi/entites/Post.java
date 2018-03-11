package com.uniovi.entites;

import java.util.Date;

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
	
	private String title;
	private String content;
	
	private Date date;
	
	@ManyToOne
	private User user;
	
	public Post() {}
	
	public Post(String title, String content) {
		super();
		this.title = title;
		this.content = content;
		this.date = new Date();
	}

	public Long getId() { return id; }
	
	public String getTitle() { return title; }
	
	public void setTitle(String title) { this.title = title; }
	
	public String getContent() { return content; }
	
	public void setContent(String content) { this.content = content; }
	
	public Date getDate() { return date; }
	
	public void setDate(Date date) { this.date = date; }
	
	public User getUser() { return user; }
	
	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", user=" + user + "]";
	}
	
}
