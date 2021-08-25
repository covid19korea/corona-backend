package com.corona.backend.infra.publicdata.xml.infection

import com.corona.backend.infra.publicdata.xml.Xml

data class Infection(
    var header: Header = Header(),
    var body: Body = Body(),
) : Xml()

data class Header(
    var resultCode: String = "",
    var resultMsg: String = "",
)

data class Body(
    var items: MutableList<Item> = mutableListOf(),
    var numOfRows: Int = 0,
    var pageNo: Int = 0,
    var totalCount: Int = 0,
)

data class Item(
    var careCnt: Int = 0, // 치료중 환자
    var clearCnt: Int = 0, // 격리 해제자
    var decideCnt: Int = 0, // 확진자
    var examCnt: Int = 0, // 검사 진행자
    var deathCnt: Int = 0, // 사망자

    var accDefRate: Double = 0.0,
    var accExamCnt: Int = 0,
    var accExamCompCnt: Int = 0,
    var createDt: String = "",
    var resutlNegCnt: Int = 0,
    var seq: Int = 0,
    var stateDt: Int = 0,
    var stateTime: String = "",
    var updateDt: String = "",
)
