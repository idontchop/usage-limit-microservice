package com.idontchop.usagelimitservice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.usagelimitservice.entities.UsageType;

public interface UsageTypeRepository extends CrudRepository<UsageType, Long> {
	
	Optional<UsageType> findByName(String name);

}
