package com.idontchop.usagelimitservice.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idontchop.usagelimitservice.dtos.RestMessage;
import com.idontchop.usagelimitservice.dtos.TrippedLimitDto;
import com.idontchop.usagelimitservice.entities.ParentLimit;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.repositories.HitRepository;

/**
 * Handles keep track of count queries.
 * 
 * For example, if we counted the hits in 60 seconds with the hard limit, and
 * now have to do it for warning, we dont want to requery the db.
 * 
 * @author nathan
 *
 */
public class HitProcessor {

	@Autowired
	HitRepository hitRepository;
	
	public HitProcessor() {}

	// records any count queries done
	private Map<Long, Long> recorded = new HashMap<>();
	
	// records any trips. If it is a hard trip, there should be only one
	// warnings can be multiple
	private List<TrippedLimitDto> trippedList = new ArrayList<>();
	
	private boolean tripped = false;
	
	public boolean isTripped() {
		return tripped;
	}
	
	public boolean isStored(Long seconds) {
		return recorded.containsKey(seconds);
	}
	
	public Long find(Long seconds) {
		return recorded.get(seconds);
	}
	
	/**
	 * See run()
	 * @return
	 */
	public HitProcessor runUntilTripped( UsageType usageType, ParentLimit[] limits, String user) {
		return run(usageType, limits, user, true);
	}
	
	/**
	 * See run()
	 */
	public HitProcessor runUntilFinished( UsageType usageType, ParentLimit[] limits, String user) {
		return run(usageType, limits, user, false);
	}
	
	/**
	 * Cycles through passed limits and runs a database query or checks the stored query values.
	 * 
	 * @param usageType
	 * @param limits
	 * @param user
	 * @param runUntilTripped
	 * @return
	 */
	private HitProcessor run( UsageType usageType, ParentLimit[] limits, String user, boolean runUntilTripped) {
		
		long result;
		for (ParentLimit p : limits ) {
			if (runUntilTripped && tripped) break;
			
			// First check for stored value
			if ( recorded.containsKey(p.getSeconds())) {
				// if recorded, check if limit reached
				if (recorded.get(p.getSeconds()) > p.getHits()) {
					// limit reached
					trippedStore(usageType, p, user);
				}
			} else {
				
				// get count of hits from hits DB
				result = hitRepository.findCountSinceByUser(
						LocalDateTime.now().minusSeconds(p.getSeconds()),
						usageType.getName(), 
						user)
						.orElse(0L);
				
				// Store in case we need later
				recorded.put(p.getSeconds(), result);
				
				// Check if limit reached
				if ( result > p.getHits() ) {
					trippedStore(usageType, p, user);
					
				}
			}
		}
		
		return this;
	}
	
	/**
	 * Stores a dto for later retrieval, also updates tripped boolean.
	 * 
	 * @param usageType
	 * @param parentLimit
	 * @param user
	 */
	private void trippedStore(UsageType usageType, ParentLimit parentLimit, String user) {
		trippedList.add(TrippedLimitDto.set(usageType, parentLimit, user));
		if ( !tripped ) tripped = true;
	}
	
	/**
	 * Converts the stored trips to a RestMessage for easy return.
	 * @return
	 */
	public RestMessage toRestMessage() {
		return RestMessage.build(
				trippedList.stream().collect(
						Collectors.toMap(
								TrippedLimitDto::getLimitName, 
								e -> e.toString(),
								(k1, k2) -> {
									return k1 + " >>>> " + k2;
								})));
			
	}

	public List<TrippedLimitDto> getTrippedList() {
		return trippedList;
	}

	public void setTrippedList(List<TrippedLimitDto> trippedList) {
		this.trippedList = trippedList;
	}
	
	
}
