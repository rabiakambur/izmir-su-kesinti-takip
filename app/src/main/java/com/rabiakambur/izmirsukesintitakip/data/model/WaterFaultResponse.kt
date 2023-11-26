package com.rabiakambur.izmirsukesintitakip.data.model

import com.google.gson.annotations.SerializedName

data class WaterFaultResponse(
    @SerializedName("IlceAdi")
    val district: String,

    @SerializedName("KesintiTarihi")
    val date: String,

    @SerializedName("Aciklama")
    val explanation: String,
)