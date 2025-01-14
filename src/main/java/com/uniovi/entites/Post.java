package com.uniovi.entites;

import java.util.Calendar;

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
	
	private String photoPath;
	
	private String date;
	
	@ManyToOne
	private User user;
	
	public Post() {
	}
	
	public void setCreationStringDate()
	{
		Calendar calendar = Calendar.getInstance();
		String aux = String.valueOf(calendar.get( Calendar.DATE )) 
				+ "/" + String.valueOf(calendar.get( Calendar.MONTH )) 
				+ "/" + String.valueOf(calendar.get( Calendar.YEAR ));
		date = aux;
	}
	
	public Post(String title, String content) {
		this();
		this.title = title;
		this.content = content;
		setCreationStringDate();
	}

	public Long getId() { return id; }

	
	public String getTitle() { return title; }
	
	public void setTitle(String title) { this.title = title; }
	
	public String getContent() { return content; }
	
	public void setContent(String content) { this.content = content; }
	
	public Boolean hasPhoto() { return photoPath != null; }
	
	public String getPhoto() { return photoPath; }
	
	public void setPhoto(String photoPath) { this.photoPath = photoPath; }
	
	public String getDate() { return date; }
	
	public void setDate(String date) { this.date = date; }
	
	public User getUser() { return user; }
	
	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", user=" + user + "]";
	}
	
}
