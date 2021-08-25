package com.corona.backend.domain.infection.res

import com.corona.backend.infra.publicdata.xml.infectionRegion.InfectionRegion
import com.corona.backend.infra.publicdata.xml.infectionRegion.Item

data class InfectionRegionRes(
    val list: List<InfectionSido>
) {
    companion object {
        fun from(xml: InfectionRegion): InfectionRegionRes {
            val items = xml.body.items

            return InfectionRegionRes(
                items.map { InfectionSido.from(it) }
            )
        }
    }
}

data class InfectionSido(
    var gubun: String, // 지역
    var incDec: String, // 오늘 신규 확진자
    var defCnt: Int, // 누적 확진자 수
    var isolClearCnt: String, // 격리 해제 수
    var deathCnt: Int, // 사망자
) {
    companion object {
        fun from(item: Item): InfectionSido {
            return InfectionSido(
                gubun = item.gubun,
                incDec = item.incDec,
                defCnt = item.defCnt,
                isolClearCnt = item.isolClearCnt,
                deathCnt = item.deathCnt,
            )
        }
    }
}
