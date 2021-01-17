package com.idontchop.usagelimitservice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.idontchop.usagelimitservice.dtos.RestMessage;
import com.idontchop.usagelimitservice.entities.HardLimit;
import com.idontchop.usagelimitservice.entities.Hit;
import com.idontchop.usagelimitservice.entities.ParentLimit;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.entities.WarningLimit;
import com.idontchop.usagelimitservice.repositories.HitRepository;
import com.idontchop.usagelimitservice.repositories.UsageTypeRepository;

@Service
public class HitService {
	
	@Autowired
	private HitRepository hitRepository; 
	
	@Autowired
	private UsageTypeRepository usageTypeRepository;
	
	@Autowired
	private ApplicationContext context;
	
	/**
	 * Adds hit and then calls processHit to check thresholds.
	 * 
	 * @param newHit
	 * @return
	 */
	public RestMessage addHit(Hit newHit) {
		
		Hit h = hitRepository
				.findByUserAndHitTypeAndCreated(
						newHit.getUser(), 
						newHit.getHitType(), 
						newHit.getCreated())
				.orElse(newHit);
		h.incrementHits();
		
		return processHit(hitRepository.save(h));
		
	}
	
	public RestMessage processHit (Hit hit) {
		HitProcessor hitProcessor = (HitProcessor) context.getBean("HitProcessor");
		
		Optional<UsageType> ut = usageTypeRepository.findByName(hit.getHitType());
		
		// if usage type doesn't match, we can't process
		if ( ut.isEmpty() ) return RestMessage.empty();
		
		if (hitProcessor.runUntilTripped(
				ut.get(), 
				ut.get().getLimits().toArray(new HardLimit[ut.get().getLimits().size()]),
				hit.getUser())
		.isTripped() ) {
			// found a hard limit
			return hitProcessor.toRestMessage();
		} else {
			// run all warnings and return
			return hitProcessor.runUntilFinished(
					ut.get(),
					ut.get().getWarnings().toArray(new WarningLimit[ut.get().getWarnings().size()]),
					hit.getUser())
			.toRestMessage();			
		}
		
		
	}
	
	

}
