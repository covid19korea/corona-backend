package com.corona.backend.domain.redis

import com.corona.backend.infra.publicdata.xml.infection.Infection
import nonapi.io.github.classgraph.json.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("infection")
class Infection(
    @Id
    val id: String,
    val data: Infection,
)
