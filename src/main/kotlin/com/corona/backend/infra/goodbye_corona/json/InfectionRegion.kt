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
            add(korea)
            add(seoul)
            add(busan)
            add(daegu)
            add(incheon)
            add(gwangju)
            add(daejeon)
            add(ulsan)
            add(sejong)
            add(gyeonggi)
            add(gangwon)
            add(chungbuk)
            add(chungnam)
            add(jeonbuk)
            add(jeonnam)
            add(gyeongbuk)
            add(gyeongnam)
            add(jeju)
            add(quarantine)
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
