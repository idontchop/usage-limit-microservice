package com.idontchop.usagelimitservice.repositories;

import javax.transaction.Transactional;

import com.idontchop.usagelimitservice.entities.WarningLimit;

@Transactional
public interface WarningLimitRepository extends LimitRepository<WarningLimit> {

}
