package com.corona.backend.domain.infection.res

import com.corona.backend.infra.publicdata.xml.infection.Infection

data class InfectionRes(
    val careCnt: Int, // 치료중 환자
    val clearCnt: Int, // 격리 해제자
    val decideCnt: Int, // 확진자
    val examCnt: Int, // 검사 진행자
    val deathCnt: Int, // 사망자

    val careCntUp: Int, // 치료중 환자
    val clearCntUp: Int, // 격리 해제자
    val decideCntUp: Int, // 확진자
    val examCntUp: Int, // 검사 진행자
    val deathCntUp: Int, // 사망자
) {
    companion object {
        fun from(xml: Infection): InfectionRes {
            val items = xml.body.items

            return InfectionRes(
                careCnt = items[0].careCnt,
                clearCnt = items[0].clearCnt,
                decideCnt = items[0].decideCnt,
                examCnt = items[0].examCnt,
                deathCnt = items[0].deathCnt,
                careCntUp = items[0].careCnt - items[1].careCnt,
                clearCntUp = items[0].clearCnt - items[1].clearCnt,
                decideCntUp = items[0].decideCnt - items[1].decideCnt,
                examCntUp = items[0].examCnt - items[1].examCnt,
                deathCntUp = items[0].deathCnt - items[1].deathCnt,
            )
        }
    }
}
