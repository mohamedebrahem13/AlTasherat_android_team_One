package com.solutionplus.altasherat.features.home.profile.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.helpers.logging.getClassLogger
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.databinding.FragmentProfileBinding
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.Item
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.ItemAdapter
import com.solutionplus.altasherat.features.home.profile.presentation.viewmodels.ProfileContract
import com.solutionplus.altasherat.features.home.profile.presentation.viewmodels.ProfileViewModel
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private lateinit var adapter: ArrayAdapter<Item>
    private val viewModel: ProfileViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        setupListView()
    }

    override fun onLoading(isLoading: Boolean) {

    }

    override fun subscribeToObservables() {

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }

    }
    private fun handleSingleEvent(event: ProfileContract.ProfileEvent) {
        logger.debug("USERRRR${event}")

        when (event) {
            is ProfileContract.ProfileEvent.UserLoaded -> {
                viewsForMenuWithSignedUser(event.user)
            }
            is ProfileContract.ProfileEvent.SignOutSuccess->{

            }
        }
    }
    private fun handleViewState(state: ProfileContract.ProfileViewState) {
        // Handle different states here
        when {
            state.isLoading -> {
                // Show loading UI
            }

            state.exception != null -> {
                // Handle error state
                val errorMessage = state.exception.message ?: "Unknown error"
                showToast("Error: $errorMessage")
            }

        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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
    private fun viewsForMenuWithSignedUser(user: User){
        binding.signOut.visibility= View.VISIBLE
        binding.profileName.visibility= View.VISIBLE
        binding.profileImage.visibility=View.VISIBLE
        binding.editProfile.visibility=View.VISIBLE
        binding.horizontalLine.visibility=View.VISIBLE
        binding.profileName.text=user.firstname
        binding.profileImage.setImageResource(user.image.id)

    }
    companion object {
        private val logger = getClassLogger()
    }

}