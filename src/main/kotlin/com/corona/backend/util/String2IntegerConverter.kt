package com.corona.backend.util

object String2IntegerConverter {

    fun convert(str: String): Int {
        return Integer.valueOf(str.replace(",", ""))
    }
}
