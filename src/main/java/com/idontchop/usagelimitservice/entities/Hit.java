package com.idontchop.usagelimitservice.entities;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
public class Hit {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String user;		// username supplied by token
	
	@NotEmpty
	private String hitType;		// Arbitrary, must match a hittype to be counted
	
	private long numHits = 0L;	// one row per second in DB
	
	private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

	public void incrementHits() {
		numHits++;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHitType() {
		return hitType;
	}

	public void setHitType(String hitType) {
		this.hitType = hitType;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public long getNumHits() {
		return numHits;
	}

	public void setNumHits(long numHits) {
		this.numHits = numHits;
	}


	

}
