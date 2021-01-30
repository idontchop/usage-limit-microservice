package com.idontchop.usagelimitservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.usagelimitservice.dtos.RestMessage;
import com.idontchop.usagelimitservice.entities.Hit;
import com.idontchop.usagelimitservice.services.HitService;

@RestController
public class HitController {
	
	@Autowired
	HitService hitService;
	
	// Hard limit reached, matches title in rest message
	@Value("${hitProcessorMessages.hardlimitreachedtitle")
	private String hardLimitReachedTitle;
	
	/**
	 * Adds hit and checks threshold. Returns proper restmessage if hit.
	 * 
	 * Returns 200 and empty json if no threshold reached.
	 * 
	 * Returns Status 202 if any threshold reached.
	 * 
	 * Returns 503 if hard limit reached
	 * 
	 * @param hit
	 * @return
	 */
	@PostMapping("/hit")
	public ResponseEntity<RestMessage> hit(@RequestBody Hit hit) {
		
		// hit should not have Id or numhits set
		if (hit.getId() > 0L || hit.getNumHits() > 0L)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(RestMessage.build("Incoming Hit malformed"));
		
		RestMessage restMessage =  hitService.addHit(hit);
		
		// Return 200 (no thresholds reached)
		if (restMessage.isEmpty()) {
			return ResponseEntity.ok(restMessage);
			
		// return 503, rest message contains a hard limit reached
		} else if (restMessage.getMessages().containsKey(hardLimitReachedTitle)){
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(restMessage);
		
		// return 202, warning reached
		} else {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(restMessage);
		}
		
	}

}
