package com.idontchop.usagelimitservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.idontchop.usagelimitservice.entities.HardLimit;
import com.idontchop.usagelimitservice.entities.Hit;
import com.idontchop.usagelimitservice.entities.ParentLimit;
import com.idontchop.usagelimitservice.entities.UsageType;
import com.idontchop.usagelimitservice.entities.WarningLimit;
import com.idontchop.usagelimitservice.repositories.HardLimitRepository;
import com.idontchop.usagelimitservice.repositories.HitRepository;
import com.idontchop.usagelimitservice.repositories.LimitRepository;
import com.idontchop.usagelimitservice.repositories.UsageTypeRepository;
import com.idontchop.usagelimitservice.repositories.WarningLimitRepository;
import com.idontchop.usagelimitservice.services.HitProcessor;

@SpringBootTest
class UsageLimitServiceApplicationTests {
	
	@Autowired
	HardLimitRepository hardRepository;
	
	@Autowired
	WarningLimitRepository warningRepository;
	
	@Autowired
	UsageTypeRepository usageTypeRepository;
	
	@Autowired
	HitRepository hitRepository;
	
	ApplicationContext context;
	
	@Test
	void contextLoads(ApplicationContext context) {
		
		this.context = context;
	}
	

	void findByHit() {
		
		String user = "guest";
		String type = "guest-query";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dt = LocalDateTime.parse("2020-12-27 21:45:19", formatter);
		
		assertTrue(hitRepository.findByUserAndHitTypeAndCreated(user, type, dt).isPresent());
		
		Hit h = hitRepository.findByUserAndHitTypeAndCreated(user, type, dt)
				.orElseGet( ()  -> {
					Hit newhit = new Hit();
					newhit.setCreated(dt);
					newhit.setHitType(type);
					newhit.setNumHits(0L);
					newhit.setUser(user);
					return newhit;
				});
		h.incrementHits();
		hitRepository.save(h);
	}
	
	
	void testHitProcessor(ApplicationContext context) {
		String type = "guest-query";
		String user = "guest";
		HitProcessor p = (HitProcessor) context.getBean("HitProcessor");
		assertTrue(usageTypeRepository.findByName(type).isPresent());
		UsageType ut = usageTypeRepository.findByName(type).orElseThrow();
		
		assertTrue ( ut.getLimits().size() > 0);
		assertTrue ( ut.getWarnings().size() > 0);
		
		assertTrue(p.runUntilTripped(ut, ut.getLimits().toArray(new HardLimit[ut.getLimits().size()]), user)
			.isStored(86400L));
		//assertEquals(7L, hitRepository.findCountSinceByUser(LocalDateTime.now().minusDays(1), type, user).get());
		assertEquals(0L, p.find(60L));
		assertTrue(p.isStored(86400L));
		assertEquals(22L, p.find(86400L));
		
		assertTrue(p.isTripped());
		assertEquals(1L, p.getTrippedList().size());

		assertEquals(5L, ut.getWarnings().toArray(new WarningLimit[ut.getWarnings().size()]));
		assertTrue(p.runUntilFinished(ut, ut.getWarnings().toArray(new WarningLimit[ut.getWarnings().size()]), user)
			.isStored(86400L));
		//assertFalse(p.isTripped());
		assertEquals(0L, p.find(60L));
		assertEquals(22L, p.find(86400L));
		assertEquals(5, p.getTrippedList().size());
		
		
	}
	
	void testParentLimitRepo() {
		assertTrue(warningRepository.findById(8L).isPresent());
		assertFalse(warningRepository.findById(9L).isPresent());
		assertTrue(hardRepository.findById(8L).isEmpty());
		assertTrue(hardRepository.findById(9L).isPresent());
	}

	
	@Transactional
	void testGuestLimits() {
		
		assertTrue(warningRepository.findById(8L).isPresent());
		WarningLimit wl = warningRepository.findById(8L).get();
		assertTrue(wl.getId() == 8L);
		UsageType ut = usageTypeRepository.findByName("guest-query").orElseThrow();
		
		assertTrue(ut.getWarnings().size() > 0);
		assertTrue(ut.getLimits().size() > 0);
	}
	
	void testCount() {
		assertTrue(hitRepository.findCountSinceByUser(LocalDateTime.now().minusDays(30), "test", "guest").get() > 0);
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
