package com.rabiakambur.izmirsukesintitakip.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.rabiakambur.izmirsukesintitakip.R
import com.rabiakambur.izmirsukesintitakip.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

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
        initObservers()
    }

    private fun initListeners() {
        binding.outlinedButton.setOnClickListener {
            viewModel.onButtonClicked(position = getSelectedDistrictPosition())
        }
        (binding.dropdownMenu.editText as AutoCompleteTextView).onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                with(getSharedPreferences().edit()) {
                    putInt("selected_district_position", position)
                    apply()
                }
            }
    }

    private fun initObservers() {
        with(viewModel) {
            waterFaultList.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = WaterFaultAdapter(it)
            }
            districtItems.observe(viewLifecycleOwner) {
                populateDropdown(it)
            }
            fetchWaterFaults(position = getSelectedDistrictPosition())
            districtItems()
        }
    }

    private fun populateDropdown(list: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, list)
        (binding.dropdownMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val autoCompleteTextView =
            (binding.dropdownMenu.editText as? MaterialAutoCompleteTextView)
        autoCompleteTextView?.setText(list[getSelectedDistrictPosition()], false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
