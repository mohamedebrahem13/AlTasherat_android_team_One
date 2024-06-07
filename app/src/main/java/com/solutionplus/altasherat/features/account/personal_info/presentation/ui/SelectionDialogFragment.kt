package com.solutionplus.altasherat.features.account.personal_info.presentation.ui

import android.os.Bundle
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.solutionplus.altasherat.common.presentation.ui.adapter.SingleSelection
import com.solutionplus.altasherat.common.presentation.ui.adapter.SingleSelectionAdapter
import com.solutionplus.altasherat.common.presentation.ui.adapter.SingleSelectionCallback
import com.solutionplus.altasherat.common.presentation.ui.adapter.SingleSelectionViewType
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseSheetFragment
import com.solutionplus.altasherat.databinding.FragmentCountrySelectionDialogBinding

class SelectionDialogFragment : BaseSheetFragment<FragmentCountrySelectionDialogBinding>(),
    SingleSelectionCallback {

    private val args: SelectionDialogFragmentArgs by navArgs()
    private val singleSelectionAdapter by lazy {
        SingleSelectionAdapter(SingleSelectionViewType.SELECTION_CHECK, this)
    }

    override fun viewInit() {
        val countries = args.countries.toList()
        val selectedIndex = args.selectedIndex

        binding.recyclerView.adapter = singleSelectionAdapter

        singleSelectionAdapter.setItems(countries)
        singleSelectionAdapter.setSelectedItem(countries[selectedIndex])

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
    }

    override fun onFragmentReady(savedInstanceState: Bundle?) {}

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

    companion object {
        const val REQUEST_KEY = "selected_country"
        const val SELECTED_COUNTRY_INDEX_KEY = "selected_country_index"
    }
}