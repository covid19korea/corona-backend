package com.corona.backend.domain.infection.res

import com.corona.backend.infra.publicdata.xml.infection.PublicInfection

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
        fun from(infection: PublicInfection): InfectionRes {
            val today = infection.body.items[0]
            val yesterday = infection.body.items[1]
            return InfectionRes(
                careCnt = today.careCnt,
                clearCnt = today.clearCnt,
                decideCnt = today.decideCnt,
                examCnt = today.examCnt,
                deathCnt = today.deathCnt,
                careCntUp = today.careCnt - yesterday.careCnt,
                clearCntUp = today.clearCnt - yesterday.clearCnt,
                decideCntUp = today.decideCnt - yesterday.decideCnt,
                examCntUp = today.examCnt - yesterday.examCnt,
                deathCntUp = today.deathCnt - yesterday.deathCnt
            )
        }
    }
}
