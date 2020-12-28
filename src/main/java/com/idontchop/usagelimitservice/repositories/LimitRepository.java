package com.idontchop.usagelimitservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.idontchop.usagelimitservice.entities.HardLimit;
import com.idontchop.usagelimitservice.entities.ParentLimit;

@NoRepositoryBean
public interface LimitRepository<T extends ParentLimit> extends
	CrudRepository <T, Long> {

	@Override
	@Query("select e from #{#entityName} as e where e.id = ?1")
	Optional<T> findById(Long id);
	
	
	
	//@Query("select e from #{#entityName} as e from ")
	//public Optional<T> findByName(String name);
	
	

}
