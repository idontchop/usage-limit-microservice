package com.idontchop.usagelimitservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.usagelimitservice.entities.Hit;

public interface HitRepository extends CrudRepository<Hit, Long> {

}
