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
import com.rabiakambur.izmirsukesintitakip.R
import com.rabiakambur.izmirsukesintitakip.data.Api
import com.rabiakambur.izmirsukesintitakip.data.District
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import com.rabiakambur.izmirsukesintitakip.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Api.retrofit.getWaterFaults().enqueue(object : Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                val body = response.body()!!
                Log.d("TAG", "onResponse: $body")

                val adapter = WaterFaultAdapter(waterFault = body)

                binding.recyclerView.adapter = adapter

                val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(
                    "com.rabiakambur.izmirsukesintitakip.ui",
                    Context.MODE_PRIVATE
                )

                (binding.dropdownMenu.editText as AutoCompleteTextView).onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        with(sharedPref.edit()) {
                            putInt("selected_district_position", position)
                            apply()
                        }
                    }
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, District.items)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
