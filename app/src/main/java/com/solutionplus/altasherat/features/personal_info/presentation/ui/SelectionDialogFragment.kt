package com.solutionplus.altasherat.features.personal_info.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutionplus.altasherat.databinding.FragmentCountrySelectionDialogBinding
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelection
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionAdapter
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionCallback
import com.solutionplus.altasherat.features.personal_info.presentation.ui.single_selection.SingleSelectionViewType

class SelectionDialogFragment : BottomSheetDialogFragment(), SingleSelectionCallback {

    private val args: SelectionDialogFragmentArgs by navArgs()

    private var _binding: FragmentCountrySelectionDialogBinding? = null
    private val binding get() = _binding!!

    private val singleSelectionAdapter by lazy {
        SingleSelectionAdapter(SingleSelectionViewType.SELECTION_CHECK, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountrySelectionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countries = args.countries.toList()
        val selectedIndex = args.selectedIndex

        binding.recyclerView.adapter = singleSelectionAdapter

        singleSelectionAdapter.setItems(countries)
        singleSelectionAdapter.setSelectedItem(countries[selectedIndex])

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

    }

    override fun onSingleItemSelected(selectedItem: SingleSelection) {
        setFragmentResult(
            requestKey = REQUEST_KEY,
            Bundle().apply {
                putInt(
                    SELECTED_COUNTRY_INDEX_KEY,
                    args.countries.indexOf(selectedItem)
                )
            })
        dismissNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUEST_KEY = "selected_country"
        const val SELECTED_COUNTRY_INDEX_KEY = "selected_country_index"
    }
}