package com.corona.backend.domain.infection.res

import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.util.CheckingCounterUtil
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
        fun from(infection: Infection): InfectionRes {
            return InfectionRes(
                careCnt = String2IntegerConverter.convert(infection.NowCase),
                clearCnt = String2IntegerConverter.convert(infection.TotalRecovered),
                decideCnt = String2IntegerConverter.convert(infection.TotalCase),
                examCnt = String2IntegerConverter.convert(infection.checkingCounter),
                deathCnt = String2IntegerConverter.convert(infection.TotalDeath),
                careCntUp = String2IntegerConverter.convert(infection.TotalCaseBefore),
                clearCntUp = String2IntegerConverter.convert(infection.TodayRecovered),
                decideCntUp = InfectionUtil.getInfectionCount(),
                examCntUp = CheckingCounterUtil.getCheckingCounter(String2IntegerConverter.convert(infection.checkingCounter)),
                deathCntUp = String2IntegerConverter.convert(infection.TodayDeath),
            )
        }
    }
}
