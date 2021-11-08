package com.corona.backend.domain.infection.res

import com.corona.backend.infra.publicdata.xml.infectionRegion.Item
import com.corona.backend.infra.publicdata.xml.infectionRegion.PublicInfectionRegion

data class InfectionRegionRes(
    val list: List<InfectionSido>
) {
    companion object {
        fun from(infectionRegion: PublicInfectionRegion): InfectionRegionRes {
            return InfectionRegionRes(
                infectionRegion.body.items.map { InfectionSido.from(it) }
            )
        }
    }
}

data class InfectionSido(
    var gubun: String, // 지역
    var incDec: Int, // 오늘 신규 확진자
    var defCnt: Int, // 누적 확진자 수
    var isolClearCnt: Int, // 격리 해제 수
    var deathCnt: Int, // 사망자
) {
    companion object {
        fun from(region: Item): InfectionSido {
            return InfectionSido(
                gubun = region.gubun,
                incDec = region.incDec,
                defCnt = region.defCnt,
                isolClearCnt = region.isolClearCnt,
                deathCnt = region.deathCnt
            )
        }
    }
}
