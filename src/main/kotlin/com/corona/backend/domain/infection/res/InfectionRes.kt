package com.corona.backend.domain.infection.res

import com.corona.backend.infra.publicdata.xml.infection.Infection
import com.corona.backend.util.CheckingCounterUtil
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

        fun from(infection: com.corona.backend.infra.goodbye_corona.json.Infection): InfectionRes {
            return InfectionRes(
                careCnt = String2IntegerConverter.convert(infection.NowCase),
                clearCnt = String2IntegerConverter.convert(infection.TodayRecovered),
                decideCnt = String2IntegerConverter.convert(infection.TotalCase),
                examCnt = String2IntegerConverter.convert(infection.checkingCounter),
                deathCnt = String2IntegerConverter.convert(infection.TotalDeath),
                careCntUp = String2IntegerConverter.convert(infection.TotalCaseBefore),
                clearCntUp = String2IntegerConverter.convert(infection.TodayRecovered),
                decideCntUp = 0,
                examCntUp = CheckingCounterUtil.getCheckingCounter(String2IntegerConverter.convert(infection.checkingCounter)),
                deathCntUp = String2IntegerConverter.convert(infection.TodayDeath),
            )
        }
    }
}
