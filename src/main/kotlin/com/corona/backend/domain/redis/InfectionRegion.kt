package com.corona.backend.domain.redis

import com.corona.backend.infra.publicdata.xml.infectionRegion.InfectionRegion
import nonapi.io.github.classgraph.json.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("infection-region")
class InfectionRegion(
    @Id
    val id: String,
    val data: InfectionRegion,
)
