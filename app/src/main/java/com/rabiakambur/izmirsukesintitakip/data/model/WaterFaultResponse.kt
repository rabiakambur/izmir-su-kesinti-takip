package com.rabiakambur.izmirsukesintitakip.data.model

import com.google.gson.annotations.SerializedName

data class WaterFaultResponse(
    @SerializedName("IlceAdi")
    val district: String,

    @SerializedName("KesintiSuresi")
    val date: String,

    @SerializedName("Tip")
    val explanation: String,

    @SerializedName("Mahalleler")
    val neighborhood: String,
)