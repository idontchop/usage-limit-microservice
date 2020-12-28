package com.idontchop.usagelimitservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.usagelimitservice.entities.Hit;
import com.idontchop.usagelimitservice.entities.ParentLimit;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.repositories.HitRepository;

@Service
public class HitService {
	
	@Autowired
	private HitRepository hitRepository; 
	
	public Hit addHit(Hit newHit) {
		
		return hitRepository.save(newHit);
		
	}
	
	public long getCountByLimit(UsageType usageType, ParentLimit limit) {
		return 0L;
	}

}
