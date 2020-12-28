package com.idontchop.usagelimitservice.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.DiscriminatorOptions;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity( name = "defined_limits")
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorOptions( force = true)
@DiscriminatorColumn( name = "limit_type", discriminatorType = DiscriminatorType.STRING)
public class ParentLimit {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String name;		// Arbitrary, returned with message
	
	@ManyToOne ( fetch = FetchType.LAZY)
	@JoinColumn (name = "usagetype_id")
	@JsonIgnore
	private UsageType usageType;
	
	
	/**
	 * Number of seconds to log hits
	 */
	private long seconds;
	
	/**
	 * Number of hits allowed in a second
	 */
	private long hits;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UsageType getUsageType() {
		return usageType;
	}

	public void setUsageType(UsageType usageType) {
		this.usageType = usageType;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public long getHits() {
		return hits;
	}

	public void setHits(long hits) {
		this.hits = hits;
	}
	
	
	
}
