package com.idontchop.usagelimitservice.dtos;

import com.idontchop.usagelimitservice.entities.ParentLimit;
import com.idontchop.usagelimitservice.entities.UsageType;

public class TrippedLimitDto {
	
	public String user;
	public Long seconds;
	public Long hitLimit;
	public String type;
	public String typeTitle;
	public String limitName;
	
	public static TrippedLimitDto set(UsageType usageType, ParentLimit limit, String user) {
		TrippedLimitDto dto = new TrippedLimitDto();
		dto.user = user;
		dto.seconds = limit.getSeconds();
		dto.hitLimit = limit.getHits();
		dto.type = usageType.getName();
		dto.typeTitle = usageType.getTitle();
		dto.limitName = limit.getName();
		
		return dto;
		
	}
	
	public String toString() {
		return type + " (" + typeTitle + "): " + limitName + " exceeded " +
				hitLimit + " in " + seconds + " seconds.";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getSeconds() {
		return seconds;
	}

	public void setSeconds(Long seconds) {
		this.seconds = seconds;
	}

	public Long getHitLimit() {
		return hitLimit;
	}

	public void setHitLimit(Long hitLimit) {
		this.hitLimit = hitLimit;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}
	
	

}
