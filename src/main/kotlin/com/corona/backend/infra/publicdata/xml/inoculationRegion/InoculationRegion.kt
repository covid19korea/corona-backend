package com.corona.backend.infra.publicdata.xml.inoculationRegion

import com.corona.backend.infra.publicdata.xml.Xml

data class InoculationRegion(
    var body: Body = Body(),
) : Xml()

data class Body(
    var dataTime: String = "",
    var items: MutableList<Item> = mutableListOf(),
)

data class Item(
    var sidoNm: String = "",
    var firstCnt: Int = 0,
    var firstTot: Int = 0,
    var secondCnt: Int = 0,
    var secondTot: Int = 0,
    var thirdCnt: Int = 0,
    var thirdTot: Int = 0,
)
