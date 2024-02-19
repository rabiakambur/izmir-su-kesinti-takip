package com.rabiakambur.izmirsukesintitakip.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rabiakambur.izmirsukesintitakip.data.Api
import com.rabiakambur.izmirsukesintitakip.data.District
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class MainViewModel : ViewModel() {

    lateinit var waterFaultResponse: List<WaterFaultResponse>

    val waterFaultList: MutableLiveData<List<WaterFaultResponse>> = MutableLiveData()

    val districtItems: MutableLiveData<List<String>> = MutableLiveData()

    fun onButtonClicked(position: Int) {
        val district = District.items[position].trUppercase()

        if (position == 0) {
            val allDistrict = waterFaultResponse
            waterFaultList.value = allDistrict
        } else {
            val newList = waterFaultResponse.filter { it.district == district }
            waterFaultList.value = newList
        }
    }

    fun fetchWaterFaults(position: Int) {
        Api.retrofit.getWaterFaults().enqueue(object : Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                waterFaultResponse = response.body()!!

                if (position == 0) {
                    waterFaultList.value = waterFaultResponse

                } else {
                    val filteredWaterFaultResponse = waterFaultResponse.filter {
                        it.district == getDistrictByPosition()
                    }
                    waterFaultList.value = filteredWaterFaultResponse
                }
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    fun districtItems() {
        districtItems.value = District.items
    }

    private fun getDistrictByPosition(): String {
        return ""//District.items[getSelectedDistrictPosition()].trUppercase()
    }

    private fun String.trUppercase(): String {
        return uppercase(Locale.forLanguageTag("tr"))
    }
}