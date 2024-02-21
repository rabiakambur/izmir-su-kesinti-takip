package com.rabiakambur.izmirsukesintitakip.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rabiakambur.izmirsukesintitakip.data.Api
import com.rabiakambur.izmirsukesintitakip.data.District
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class MainViewModel: ViewModel() {

    lateinit var waterFaultResponse: List<WaterFaultResponse>

    private val _homeViewState: MutableLiveData<HomeViewState> = MutableLiveData()

    val homeViewState: LiveData<HomeViewState> = _homeViewState

    fun onButtonClicked(position: Int) {
        val district = District.items[position].trUppercase()

        if (position == 0) {
            _homeViewState.value = HomeViewState(waterFaultList = waterFaultResponse)
        } else {
            val newList = waterFaultResponse.filter { it.district == district }
            _homeViewState.value = HomeViewState(waterFaultList = newList)
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
                    _homeViewState.value = HomeViewState(waterFaultList = waterFaultResponse)
                } else {
                    val filteredWaterFaultResponse = waterFaultResponse.filter {
                        it.district == getDistrictByPosition()
                    }
                    _homeViewState.value = HomeViewState(waterFaultList = filteredWaterFaultResponse)
                }
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    fun districtItems() {
        val viewState = HomeViewState(districtItems = District.items)
        _homeViewState.value = viewState
    }

    private fun getDistrictByPosition(): String {
        return ""//District.items[getSelectedDistrictPosition()].trUppercase()
    }

    private fun String.trUppercase(): String {
        return uppercase(Locale.forLanguageTag("tr"))
    }
}