package com.rabiakambur.izmirsukesintitakip.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
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
            viewModel.onButtonClicked(position = viewModel.getSelectedDistrictPosition())
        }
        (binding.dropdownMenu.editText as AutoCompleteTextView).onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.updateSelectedDistrictPosition(position)
            }
    }

    private fun initObservers() {
        with(viewModel) {
            homeViewState.observe(viewLifecycleOwner) {
                binding.recyclerView.adapter = WaterFaultAdapter(it.waterFaultList)
                populateDropdown(it.districtItems)
                binding.textViewEmpty.isVisible = it.isEmptyViewVisible()
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
        autoCompleteTextView?.setText(list[viewModel.getSelectedDistrictPosition()], false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
