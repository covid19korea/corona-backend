package com.corona.backend.infra.publicdata.xml.inoculation

import com.corona.backend.infra.publicdata.xml.Xml

data class Inoculation(
    var body: Body = Body(),
) : Xml()

data class Body(
    var dataTime: String = "",
    var items: MutableList<Item> = mutableListOf(),
)

data class Item(
    var tpcd: String = "",
    var firstCnt: Int = 0,
    var secondCnt: Int = 0,
    var thirdCnt: Int = 0,
)
