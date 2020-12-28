package com.idontchop.usagelimitservice.repositories;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.idontchop.usagelimitservice.entities.Hit;

public interface HitRepository extends CrudRepository<Hit, Long> {

	@Query("select sum(h.numHits) from Hit h where h.created >= :since and h.hitType = :hittype and h.user = :user")
	Optional<Long> findCountSinceByUser(
			@Param("since") LocalDateTime since,
			@Param("hittype") String hittype,
			@Param("user") String user);
	
	Optional<Hit> findByUserAndHitTypeAndCreated (
			String user, String hittype, LocalDateTime created);
}
