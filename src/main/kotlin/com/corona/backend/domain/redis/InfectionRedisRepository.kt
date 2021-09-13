package com.corona.backend.domain.redis

import org.springframework.data.repository.CrudRepository

interface InfectionRedisRepository : CrudRepository<Infection, String>
