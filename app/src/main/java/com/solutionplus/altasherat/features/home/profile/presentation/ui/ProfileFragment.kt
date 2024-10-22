package com.solutionplus.altasherat.features.home.profile.presentation.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.android.extentions.showSnackBar
import com.solutionplus.altasherat.common.presentation.ui.base.fragment.BaseFragment
import com.solutionplus.altasherat.common.presentation.viewmodel.ViewAction
import com.solutionplus.altasherat.databinding.FragmentProfileBinding
import com.solutionplus.altasherat.features.splash.presention.ui.activity.MainActivity
import com.solutionplus.altasherat.features.auth.presentation.AuthActivity
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.Item
import com.solutionplus.altasherat.features.home.profile.presentation.ui.adapter.ItemAdapter
import com.solutionplus.altasherat.features.home.profile.presentation.viewmodels.ProfileContract
import com.solutionplus.altasherat.features.home.profile.presentation.viewmodels.ProfileViewModel
import com.solutionplus.altasherat.features.services.user.domain.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(), ItemAdapter.ItemClickListener {
    private lateinit var adapter: ArrayAdapter<Item>
    private val viewModel: ProfileViewModel by viewModels()

    override fun onFragmentReady(savedInstanceState: Bundle?) {
        binding.editProfile.setOnClickListener {
            viewModel.onActionTrigger(ProfileContract.ProfileAction.EditProfile)
        }
    }

    override fun onLoading(isLoading: Boolean) {}

    override fun subscribeToObservables() {

        collectFlowWithLifecycle(viewModel.singleEvent) { event ->
            handleSingleEvent(event)
        }
        collectFlowWithLifecycle(viewModel.viewState) { state ->
            handleViewState(state)
        }

    }

    private fun handleSingleEvent(event: ProfileContract.ProfileEvent) {
        when (event) {

            is ProfileContract.ProfileEvent.SignOutSuccess -> {
                //sign out
                Intent(requireActivity(), MainActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            ProfileContract.ProfileEvent.AboutUsNavigation -> {
                findNavController().navigate(R.id.action_fragment_profile_to_fragmentAbout)
            }

            ProfileContract.ProfileEvent.ChangePasswordNavigation -> {
                val action = ProfileFragmentDirections.actionFragmentProfileToEditPasswordFragment()
                findNavController().navigate(action)
            }

            ProfileContract.ProfileEvent.ContactUsNavigation -> {
                val action = ProfileFragmentDirections.actionFragmentProfileToContactUsFragment()
                findNavController().navigate(action)
            }

            ProfileContract.ProfileEvent.EditProfileNavigation -> {
                val action = ProfileFragmentDirections.actionFragmentProfileToPersonalInfoFragment()
                findNavController().navigate(action)
            }

            ProfileContract.ProfileEvent.LanguageSelectionNavigation ->
                findNavController().navigate(ProfileFragmentDirections.actionFragmentProfileToLanguageSettingsFragment())

            ProfileContract.ProfileEvent.PrivacyPolicyNavigation -> {
                findNavController().navigate(R.id.action_fragment_profile_to_fragmentPrivacyPolicies)
            }

            ProfileContract.ProfileEvent.TermsAndConditionsNavigation -> {
                findNavController().navigate(R.id.action_fragment_profile_to_fragmentTermsCondition)

            }

            ProfileContract.ProfileEvent.Login -> {
                Intent(requireActivity(), AuthActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
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
                handleException(exception = state.exception, action = state.action)
            }

            state.user != null -> {
                viewsForMenuWithSignedUser(state.user)
                // Update the list of items with the user information
                setupListView(state.user)
            }

            else -> {
                // User not signed in, setup list view without user information
                setupListView(null)
            }

        }
    }

    override fun viewInit() {
        // Get the current text of the TextView
        val currentText = binding.currentVersion.text.toString()

        // Append the app version string to the current text
        val updatedText = "$currentText ${getAppVersion(requireContext())}V"

        // Set the updated text back to the TextView
        binding.currentVersion.text = updatedText

    }

    private fun getItems(user: User?): List<Item> {
        val items = mutableListOf<Item>()

        // Add default items
        items.add(Item(1, R.drawable.login, getString(R.string.login_text)))
        items.add(Item(2, R.drawable.about, getString(R.string.about_us)))
        items.add(Item(3, R.drawable.contactus, getString(R.string.contact_us)))
        items.add(Item(4, R.drawable.terms, getString(R.string.terms_and_conditions)))
        items.add(Item(5, R.drawable.policy, getString(R.string.privacy_policy)))
        items.add(Item(6, R.drawable.language, getString(R.string.language)))

        // Update the first item based on user status
        if (user != null) {
            // Replace the first item for signed-in users
            items[0] = Item(7, R.drawable.editpass, getString(R.string.Change_Password))
        }

        return items
    }

    private fun setupListView(user: User?) {
        adapter = ItemAdapter(requireContext(), getItems(user), this)
        binding.listView.adapter = adapter
    }

    private fun viewsForMenuWithSignedUser(user: User) {
        with(binding) {
            signOut.visibility = View.VISIBLE
            profileName.visibility = View.VISIBLE
            profileImage.visibility = View.VISIBLE
            editProfile.visibility = View.VISIBLE
            horizontalLine.visibility = View.VISIBLE
            val fullName =
                getString(R.string.user_full_name, user.firstname, user.middlename, user.lastname)
            profileName.text = fullName
            profileImage.load(user.image.path) {
                crossfade(true)
                placeholder(R.drawable.ic_no_profile_image)
                error(R.drawable.ic_no_profile_image)
                transformations(CircleCropTransformation())
            }
            signOut.setOnClickListener {
                viewModel.onActionTrigger(ProfileContract.ProfileAction.SignOut)
            }
            verificationRootLayout.isVisible = !user.isEmailVerified
        }


    }

    override fun onItemClick(item: Item) {
        val action = when (item.id) {
            1 -> ProfileContract.ProfileAction.Login
            2 -> ProfileContract.ProfileAction.AboutUs
            3 -> ProfileContract.ProfileAction.ContactUs
            4 -> ProfileContract.ProfileAction.TermsAndConditions
            5 -> ProfileContract.ProfileAction.PrivacyPolicy
            6 -> ProfileContract.ProfileAction.Language
            7 -> ProfileContract.ProfileAction.ChangePassword

            else -> null
        }

        action?.let {
            viewModel.onActionTrigger(it)
        }
    }

    private fun getAppVersion(context: Context): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "Unknown"
    }

    override fun onRetryAction(action: ViewAction?, message: String) {
        showSnackBar(message) {
            action?.let { viewModel.processIntent(it as ProfileContract.ProfileAction) }
        }
    }
}