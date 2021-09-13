package com.corona.backend.domain.redis

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("infection")
class Infection(
    @Id
    val id: String,
    val data: Infection,
)
