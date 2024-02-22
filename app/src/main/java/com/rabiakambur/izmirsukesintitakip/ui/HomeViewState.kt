package com.rabiakambur.izmirsukesintitakip.ui

import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse

data class HomeViewState(
    val waterFaultList: List<WaterFaultResponse> = emptyList(),
    val districtItems: List<String> = emptyList()
) {
    fun isEmptyViewVisible(): Boolean {
        return waterFaultList.isEmpty()
    }
}