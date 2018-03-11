package com.uniovi.entites;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TUsers")
public class User {
	
	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String email;
	private String name;
	private String role;

	private String password;
	
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private Set<FriendshipRequest> requestSended;
	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private Set<FriendshipRequest> requestReceived;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Post> posts;

	public User() {}
	
	public User(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}

	public Long getId() { return id; }

//	public void setId(long id) { this.id = id; } 

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getPasswordConfirm() { return passwordConfirm; }

	public void setPasswordConfirm(String passwordConfirm) { this.passwordConfirm = passwordConfirm; }

	public String getRole() { return role; }

	public Set<FriendshipRequest> getRequestSended() { return requestSended; }

	public Set<FriendshipRequest> getRequestReceived() { return requestReceived; }

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", role=" + role + ", password=" + password
				+ "]";
	}
	
}