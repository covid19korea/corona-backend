package com.corona.backend.util

object InfectionUtil {

    private var todayInfectionCount = 0

    fun getInfectionCount(): Int {
        return todayInfectionCount
    }

    fun cacheInfectionCount(infectionCount: Int) {
        todayInfectionCount = infectionCount
    }
}
