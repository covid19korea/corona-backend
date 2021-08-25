package com.corona.backend.domain.inoculation.res

import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.infra.publicdata.xml.inoculationRegion.Item

data class InoculationRegionRes(
    val list: List<InoculationSido>,
) {
    companion object {
        fun from(xml: InoculationRegion): InoculationRegionRes {
            val items = xml.body.items

            return InoculationRegionRes(
                items.map { InoculationSido.from(it) }
            )
        }
    }
}

data class InoculationSido(
    val sidoNm: String,
    val firstTot: Int,
    val secondTot: Int,
) {
    companion object {
        fun from(item: Item): InoculationSido {
            return InoculationSido(
                sidoNm = item.sidoNm,
                firstTot = item.firstTot,
                secondTot = item.secondTot,
            )
        }
    }
}
