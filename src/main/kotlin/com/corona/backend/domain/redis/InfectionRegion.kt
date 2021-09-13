package com.corona.backend.domain.redis

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("infection-region")
class InfectionRegion(
    @Id
    val id: String,
    val data: InfectionRegion,
)
