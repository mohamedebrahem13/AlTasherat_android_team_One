package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutionplus.altasherat.databinding.FragmentCountrySelectionDialogBinding
import com.solutionplus.altasherat.features.personal_info.domain.models.Country
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionAdapter
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionCallback
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionViewType

class SelectionDialogFragment(
    private val singleSelectionCallback: SingleSelectionCallback
) : BottomSheetDialogFragment() {

    private var _binding: FragmentCountrySelectionDialogBinding? = null
    private val binding get() = _binding!!

    private val singleSelectionAdapter by lazy {
        SingleSelectionAdapter(SingleSelectionViewType.SELECTION_CHECK, singleSelectionCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountrySelectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = arguments?.getParcelableArrayList(ARG_COUNTRIES, Country::class.java)!!

        binding.recyclerView.adapter = singleSelectionAdapter

        singleSelectionAdapter.setItems(countries)
        singleSelectionAdapter.setSelectedItem(countries.find { it.isSelected })

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

    }

    companion object {
        fun newInstance(
            singleSelectionCallback: SingleSelectionCallback, countries: ArrayList<Country>
        ): SelectionDialogFragment =
            SelectionDialogFragment(singleSelectionCallback).apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_COUNTRIES, countries)
                }
            }

        const val ARG_COUNTRIES = "countries"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}