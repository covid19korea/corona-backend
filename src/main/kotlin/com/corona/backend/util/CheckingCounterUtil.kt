package com.corona.backend.util

object CheckingCounterUtil {

    var yesterdayCheckingCounter = 910419

    fun getCheckingCounter(todayCheckingCounter: Int): Int {
        return todayCheckingCounter - yesterdayCheckingCounter
    }

    fun setCheckingCounter(checkingCounter: Int) {
        yesterdayCheckingCounter = checkingCounter
    }
}
