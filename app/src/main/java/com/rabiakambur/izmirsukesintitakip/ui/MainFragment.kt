package com.rabiakambur.izmirsukesintitakip.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rabiakambur.izmirsukesintitakip.data.Api
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

        Api.retrofit.getWaterFaults().enqueue(object: Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                val x = 5
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
               val x = 5
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
