package com.corona.backend.infra.goodbye_corona.json

data class InfectionRegion(
    val resultCode: String,
    val resultMessage: String,
    val korea: Region,
    val seoul: Region,
    val busan: Region,
    val daegu: Region,
    val incheon: Region,
    val gwangju: Region,
    val daejeon: Region,
    val ulsan: Region,
    val sejong: Region,
    val gyeonggi: Region,
    val gangwon: Region,
    val chungbuk: Region,
    val chungnam: Region,
    val jeonbuk: Region,
    val jeonnam: Region,
    val gyeongbuk: Region,
    val gyeongnam: Region,
    val jeju: Region,
    val quarantine: Region,
) {

    fun getList(): MutableList<Region> {
        return mutableListOf<Region>().apply {
            add(quarantine)
            add(jeju)
            add(gyeongnam)
            add(gyeongbuk)
            add(jeonnam)
            add(jeonbuk)
            add(chungnam)
            add(chungbuk)
            add(gangwon)
            add(gyeonggi)
            add(sejong)
            add(ulsan)
            add(daejeon)
            add(gwangju)
            add(incheon)
            add(daegu)
            add(busan)
            add(seoul)
            add(korea)
        }
    }

    data class Region(
        val countryName: String,
        val newCase: String,
        val totalCase: String,
        val recovered: String,
        val death: String,
        val percentage: String,
        val newCcase: String,
        val newFcase: String,
    )
}
