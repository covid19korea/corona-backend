package com.corona.backend.domain.infection.res

import com.corona.backend.infra.goodbye_corona.json.InfectionRegion

data class InfectionRegionRes(
    val list: List<InfectionSido>
) {
    companion object {
        fun from(infectionRegion: InfectionRegion): InfectionRegionRes {
            return InfectionRegionRes(
                infectionRegion.getList().map { InfectionSido.from(it) }
            )
        }
    }
}

data class InfectionSido(
    var gubun: String, // 지역
    var incDec: String, // 오늘 신규 확진자
    var defCnt: String, // 누적 확진자 수
    var isolClearCnt: String, // 격리 해제 수
    var deathCnt: String, // 사망자
) {
    companion object {
        fun from(region: InfectionRegion.Region): InfectionSido {
            return InfectionSido(
                gubun = region.countryName,
                incDec = region.newCase,
                defCnt = region.totalCase,
                isolClearCnt = region.recovered,
                deathCnt = region.death,
            )
        }
    }
}
