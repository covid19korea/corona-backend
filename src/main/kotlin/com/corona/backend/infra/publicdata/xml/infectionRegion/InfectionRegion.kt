package com.corona.backend.infra.publicdata.xml.infectionRegion

import com.corona.backend.infra.publicdata.xml.Xml

data class InfectionRegion(
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
    var gubun: String = "", // 지역
    var incDec: String = "", // 오늘 신규 확진자
    var defCnt: Int = 0, // 누적 확진자 수
    var isolClearCnt: String = "", // 격리 해제 수
    var deathCnt: Int = 0, // 사망자

    var createDt: String = "",
    var gubunCn: String = "",
    var gubunEn: String = "",
    var isolIngCnt: Int = 0,
    var localOccCnt: Int = 0,
    var overFlowCnt: Int = 0,
    var qurRate: String = "",
    var seq: Int = 0,
    var stdDay: String = "",
    var updateDt: String = "",
)
