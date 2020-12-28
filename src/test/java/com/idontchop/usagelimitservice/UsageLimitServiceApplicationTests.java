package com.idontchop.usagelimitservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.idontchop.usagelimitservice.entities.HardLimit;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.entities.WarningLimit;
import com.idontchop.usagelimitservice.repositories.HardLimitRepository;
import com.idontchop.usagelimitservice.repositories.LimitRepository;
import com.idontchop.usagelimitservice.repositories.UsageTypeRepository;
import com.idontchop.usagelimitservice.repositories.WarningLimitRepository;

@SpringBootTest
class UsageLimitServiceApplicationTests {
	
	@Autowired
	HardLimitRepository hardRepository;
	
	@Autowired
	WarningLimitRepository warningRepository;
	
	@Autowired
	UsageTypeRepository usageTypeRepository;
	
	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void testGuestLimits() {
		
		assertTrue(warningRepository.findById(8L).isPresent());
		WarningLimit wl = warningRepository.findById(8L).get();
		assertTrue(wl.getId() == 8L);
		UsageType ut = usageTypeRepository.findByName("guest-query").orElseThrow();
		
		assertTrue(ut.getWarnings().size() > 0);
		assertTrue(ut.getLimits().size() > 0);
	}
	
	void loadGuestLimits() {
		
		UsageType ut = usageTypeRepository.findById(2L).get();
		WarningLimit wl = new WarningLimit();
		wl.setName("guest_query_warning");
		wl.setSeconds(60L);
		wl.setHits(50L);
		wl.setUsageType(ut);
		
		warningRepository.save(wl);
		
		HardLimit hl = new HardLimit();
		hl.setName("guest_query_hard_limit");
		hl.setSeconds(60L);
		hl.setHits(60L);
		hl.setUsageType(ut);
		
		hardRepository.save(hl);
		
	}



}
