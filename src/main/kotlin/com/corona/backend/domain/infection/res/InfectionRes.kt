package com.corona.backend.domain.infection.res

import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.util.InfectionUtil
import com.corona.backend.util.String2IntegerConverter

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
//        fun from(infection: PublicInfection): InfectionRes {
//            val today = infection.body.items[0]
//            val yesterday = infection.body.items[1]
//            return InfectionRes(
//                careCnt = 0,
//                clearCnt = 0,
//                decideCnt = today.decideCnt,
//                examCnt = 0,
//                deathCnt = today.deathCnt,
//                careCntUp = 0,
//                clearCntUp = 0,
//                decideCntUp = today.decideCnt - yesterday.decideCnt,
//                examCntUp = 0,
//                deathCntUp = today.deathCnt - yesterday.deathCnt
//            )
//        }

        fun from(infection: Infection?): InfectionRes {
            return InfectionRes(
                careCnt = String2IntegerConverter.convert(infection!!.NowCase),
                clearCnt = String2IntegerConverter.convert(infection.TotalRecovered),
                decideCnt = String2IntegerConverter.convert(infection.TotalCase),
                examCnt = 0,
                deathCnt = String2IntegerConverter.convert(infection.TotalDeath),
                careCntUp = String2IntegerConverter.convert(infection.TotalCaseBefore),
                clearCntUp = 0,
                decideCntUp = InfectionUtil.getInfectionCount(),
                examCntUp = 0,
                deathCntUp = 0,
            )
        }
    }
}
