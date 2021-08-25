package com.corona.backend.domain.inoculation.res

import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation

data class InoculationRes(

    val firstCnt: Int = 0,
    val secondCnt: Int = 0,
    val firstCntUp: Int = 0,
    val secondCntUp: Int = 0,

) {
    companion object {
        fun from(xml: Inoculation): com.corona.backend.domain.inoculation.res.InoculationRes {
            val items = xml.body.items

            return InoculationRes(
                firstCnt = items[2].firstCnt,
                secondCnt = items[2].secondCnt,
                firstCntUp = items[0].firstCnt,
                secondCntUp = items[0].secondCnt,
            )
        }
    }
}
