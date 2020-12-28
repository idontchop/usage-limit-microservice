package com.idontchop.usagelimitservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/hit")
	public RestMessage hit(@RequestBody Hit hit) {
		return hitService.addHit(hit);
		
	}

}
