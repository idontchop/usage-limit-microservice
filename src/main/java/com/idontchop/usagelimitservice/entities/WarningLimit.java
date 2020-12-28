package com.idontchop.usagelimitservice.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

/**
 * Joined into UsageType.
 * 
 * UsageType can have multiple limits. When a user exceeds the number of hits in the
 * number of seconds, an exception occurs.
 * 
 * @author nathan
 *
 */
@Entity
@DiscriminatorValue("warning")
public class WarningLimit extends ParentLimit {
	

}
