package com.rabiakambur.izmirsukesintitakip.data

import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("arizakaynaklisukesintileri")
    fun getWaterFaults(): Call<List<WaterFaultResponse>>
}