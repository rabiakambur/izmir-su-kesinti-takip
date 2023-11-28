package com.rabiakambur.izmirsukesintitakip.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.rabiakambur.izmirsukesintitakip.R
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

        Api.retrofit.getWaterFaults().enqueue(object : Callback<List<WaterFaultResponse>> {
            override fun onResponse(
                call: Call<List<WaterFaultResponse>>,
                response: Response<List<WaterFaultResponse>>
            ) {
                val body = response.body()!!
                Log.d("TAG", "onResponse: $body")

                val adapter = WaterFaultAdapter(waterFault = body)

                binding.recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<WaterFaultResponse>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })

        val items = listOf(
            "Aliağa",
            "Balçova",
            "Bayındır",
            "Bayraklı",
            "Bergama",
            "Beydağ",
            "Bornova",
            "Buca",
            "Çeşme",
            "Çiğli",
            "Dikili",
            "Foça",
            "Gaziemir",
            "Güzelbahçe",
            "Karabağlar",
            "Karaburun",
            "Karşıyaka",
            "Kemalpaşa",
            "Kınık",
            "Kiraz",
            "Konak",
            "Menderes",
            "Menemen",
            "Narlıdere",
            "Ödemiş",
            "Seferihisar",
            "Selçuk",
            "Tire",
            "Torbalı",
            "Urla"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
