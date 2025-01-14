package com.uniovi.entites;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TFriendshipRequest", 
uniqueConstraints = { @UniqueConstraint(columnNames = 
					{ "sender_id", "receiver_id" }) 
})
public class FriendshipRequest {
	
	@Id
	@GeneratedValue
	Long id;
	boolean accepted;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	User sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	User receiver;
	
	public FriendshipRequest() { }

	public FriendshipRequest(User sender, User receiver) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		accepted = false;
	}

	public Long getId() { return id; }
	
	public boolean isAccepted() { return accepted; }

	public void setAccepted(boolean accepted) { this.accepted = accepted; }

	public User getSender() { return sender; }

	public void setSender(User sender) { this.sender = sender; }

	public User getReceiver() { return receiver; }

	public void setReceiver(User receiver) { this.receiver = receiver; }

	@Override
	public String toString() {
		return "FriendshipRequest [id=" + id + ", accepted=" + accepted + ", sender=" + sender + ", receiver="
				+ receiver + "]";
	}

}
