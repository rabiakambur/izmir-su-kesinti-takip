package com.rabiakambur.izmirsukesintitakip.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rabiakambur.izmirsukesintitakip.data.Api
import com.rabiakambur.izmirsukesintitakip.data.District
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale


class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    var waterFaultResponse: List<WaterFaultResponse> = emptyList()

    private val _homeViewState: MutableLiveData<HomeViewState> = MutableLiveData()

    val homeViewState: LiveData<HomeViewState> = _homeViewState

    fun onButtonClicked(position: Int) {
        val district = District.items[position].trUppercase()

        if (position == 0) {
            updateViewState(waterFaultResponse)
        } else {
            val newList = waterFaultResponse.filter { it.district == district }
            updateViewState(newList)
        }
    }

    fun fetchWaterFaults(position: Int) {
        Api.retrofit.getWaterFaults().enqueue(object : Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                if (response.body() != null) {
                  waterFaultResponse = response.body()!!
                }

                if (position == 0) {
                    updateViewState(waterFaultResponse)
                } else {
                    val filteredWaterFaultResponse = waterFaultResponse.filter {
                        it.district == getDistrictByPosition()
                    }
                    updateViewState(filteredWaterFaultResponse)
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

    fun getSelectedDistrictPosition(): Int {
        return getSharedPreferences().getInt("selected_district_position", 0)
    }

    fun updateSelectedDistrictPosition(position: Int) {
        with(getSharedPreferences().edit()) {
            putInt("selected_district_position", position)
            apply()
        }
    }

    private fun updateViewState(list: List<WaterFaultResponse>) {
        _homeViewState.value = _homeViewState.value?.copy(waterFaultList = list) ?: HomeViewState(waterFaultList = list)
    }

    private fun getDistrictByPosition(): String {
        return District.items[getSelectedDistrictPosition()].trUppercase()
    }

    private fun String.trUppercase(): String {
        return uppercase(Locale.forLanguageTag("tr"))
    }

    private fun getSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(
            "com.rabiakambur.izmirsukesintitakip.ui",
            Context.MODE_PRIVATE
        )
    }
}