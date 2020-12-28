package com.idontchop.usagelimitservice.repositories;

import javax.transaction.Transactional;

import com.idontchop.usagelimitservice.entities.HardLimit;

@Transactional
public interface HardLimitRepository extends LimitRepository<HardLimit> {

}
