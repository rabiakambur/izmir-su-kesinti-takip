package com.rabiakambur.izmirsukesintitakip.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.rabiakambur.izmirsukesintitakip.R
import com.rabiakambur.izmirsukesintitakip.data.Api
import com.rabiakambur.izmirsukesintitakip.data.District
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import com.rabiakambur.izmirsukesintitakip.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    lateinit var waterFaultResponse: List<WaterFaultResponse>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        populateDropdown()
        fetchWaterFaults()
    }

    private fun initListeners() {
        binding.outlinedButton.setOnClickListener {
            val position = getSelectedDistrictPosition()
            val district = District.items[position].trUppercase()

            if (position == 0) {
                val allDistrict = waterFaultResponse
                binding.recyclerView.adapter = WaterFaultAdapter(allDistrict)
            } else {
                val newList = waterFaultResponse.filter { it.district == district }

                binding.recyclerView.adapter = WaterFaultAdapter(newList)
            }
        }

        (binding.dropdownMenu.editText as AutoCompleteTextView).onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                with(getSharedPreferences().edit()) {
                    putInt("selected_district_position", position)
                    apply()
                }
            }
    }

    private fun populateDropdown() {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, District.items)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val autoCompleteTextView =
            (binding.dropdownMenu.editText as? MaterialAutoCompleteTextView)
        autoCompleteTextView?.setText(District.items[getSelectedDistrictPosition()], false)
    }

    private fun fetchWaterFaults() {
        Api.retrofit.getWaterFaults().enqueue(object : Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                waterFaultResponse = response.body()!!

                if (getSelectedDistrictPosition() == 0) {
                    val adapter = WaterFaultAdapter(waterFault = waterFaultResponse)
                    binding.recyclerView.adapter = adapter
                } else {
                    val filteredWaterFaultResponse = waterFaultResponse.filter {
                        it.district == getDistrictByPosition()
                    }
                    val adapter = WaterFaultAdapter(waterFault = filteredWaterFaultResponse)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    private fun getDistrictByPosition(): String {
        return District.items[getSelectedDistrictPosition()].trUppercase()
    }

    private fun getSelectedDistrictPosition(): Int {
        return getSharedPreferences().getInt("selected_district_position", 0)
    }

    private fun getSharedPreferences(): SharedPreferences {
        return requireActivity().getSharedPreferences(
            "com.rabiakambur.izmirsukesintitakip.ui",
            Context.MODE_PRIVATE
        )
    }

    private fun String.trUppercase(): String {
        return uppercase(Locale.forLanguageTag("tr"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
