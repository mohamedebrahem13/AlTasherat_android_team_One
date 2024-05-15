package com.solutionplus.altasherat.features.auth.signup.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.FragmentViewpagerBinding

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewpagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewpagerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val fragments = listOf<Fragment>(SignUpFragment(), LoginFragment())
        val adapter =
            ViewPagerAdapter(fragments, requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabLayout = binding.tabLayout


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = getString(R.string.new_account)

                }
                1 -> {
                    tab.text = getString(R.string.login)
                }
            }

        }.attach()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}