package com.corona.backend.domain.redis

import org.springframework.data.repository.CrudRepository

interface InfectionRegionRedisRepository: CrudRepository<InfectionRegion, String>