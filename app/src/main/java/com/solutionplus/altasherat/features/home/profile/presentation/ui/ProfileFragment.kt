package com.solutionplus.altasherat.features.home.profile.presentation.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileBinding
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.Item
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.ItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var adapter: ArrayAdapter<Item>

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupListView()
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {
    }

    override fun viewInit() {
    }
    private fun getItems(): List<Item> {
        // Replace this with your logic to get the list of items for the ListView
        return listOf(
            Item( R.drawable.editpass,getString(R.string.Change_Password)),
            Item( R.drawable.about, getString(R.string.about_us)),
            Item( R.drawable.contactus, getString(R.string.contact_us)),
            Item( R.drawable.terms,  getString(R.string.terms_and_conditions)),
            Item( R.drawable.policy,  getString(R.string.privacy_policy)),
            Item( R.drawable.language,  getString(R.string.language))

        )
    }
    private fun setupListView() {
        adapter = ItemAdapter(requireContext(), getItems())
        binding.listView.adapter = adapter
    }
}