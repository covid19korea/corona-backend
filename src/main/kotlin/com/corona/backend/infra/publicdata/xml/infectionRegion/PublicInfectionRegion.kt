package com.corona.backend.infra.publicdata.xml.infectionRegion

import com.corona.backend.infra.publicdata.xml.Xml

data class PublicInfectionRegion(
    var header: Header = Header(),
    var body: Body = Body(),
) : Xml()

data class Header(
    var resultCode: Int = 0,
    var resultMsg: String = ""
)

data class Body(
    var items: MutableList<Item> = mutableListOf(),
    var numOfRows: Int = 0,
    var pageNo: Int = 0,
    var totalCount: Int = 0,
)

data class Item(
    var createDt: String = "",
    var deathCnt: Int = 0,
    var defCnt: Int = 0,
    var gubun: String = "",
    var gubunCn: String = "",
    var gubunEn: String = "",
    var incDec: Int = 0,
    var isolClearCnt: Int = 0,
    var isolIngCnt: Int = 0,
    var localOccCnt: Int = 0,
    var overFlowCnt: Int = 0,
    var qurRate: String = "",
    var seq: Int = 0,
    var stdDay: String = "",
    var updateDt: String = "",
)
