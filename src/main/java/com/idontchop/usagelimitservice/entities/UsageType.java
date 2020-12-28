package com.idontchop.usagelimitservice.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;




/**
 * This configures a usage type. The Usage Type must be supplied with each hit. Recent usage
 * of this type is then compared to settings in the type to determine if restriction should be
 * imposed.
 * 
 * @author nathan
 *
 */
@Entity
public class UsageType {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String name;		// Must match the Hit.HitType to be relevant
	
	@NotEmpty
	private String title;		// Title of Type
	
	private boolean active;
	
	/**
	 * Sets a threshold for issuing a warning
	 */
	@OneToMany (	cascade = CascadeType.ALL,
					targetEntity = WarningLimit.class,
					orphanRemoval = true,
					mappedBy = "usageType",
					fetch = FetchType.EAGER)	
	private List<WarningLimit> warnings = new ArrayList<>();		// list of traits for this user, can be empty

	/**
	 * Sets a threshold for imposing a limit
	 */
	@OneToMany (	cascade = CascadeType.ALL,
			targetEntity = HardLimit.class,
			orphanRemoval = true,
			mappedBy = "usageType",
			fetch = FetchType.EAGER)	
	private List<HardLimit> limits = new ArrayList<>();

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<WarningLimit> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<WarningLimit> warnings) {
		this.warnings = warnings;
	}

	public List<HardLimit> getLimits() {
		return limits;
	}

	public void setLimits(List<HardLimit> limits) {
		this.limits = limits;
	}
	
	

}
