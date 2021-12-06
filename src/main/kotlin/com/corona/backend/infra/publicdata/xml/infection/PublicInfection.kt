package com.corona.backend.infra.publicdata.xml.infection

import com.corona.backend.infra.publicdata.xml.Xml

data class PublicInfection(
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
//    var accDefRate: Double = 0.0,
    var accExamCnt: Int = 0, //
//    var accExamCompCnt: Int = 0,
//    var careCnt: Int = 0,
//    var clearCnt: Int = 0,
    var createDt: String = "", //
    var deathCnt: Int = 0, //
    var decideCnt: Int = 0, //
//    var examCnt: Int = 0,
//    var resutlNegCnt: Int = 0,
    var seq: Int = 0, //
    var stateDt: Int = 0, //
    var stateTime: String = "", //
    var updateDt: String = "", //
)
