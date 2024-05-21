package com.solutionplus.altasherat.features.splash.presention

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.FragmentViewPagerBinding
import kotlin.math.abs


class ViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewpager
        setupViewPager()
        setupIndicators()
        setCurrentIndicator(0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
    }

    private fun setupViewPager() {
        val fragments = listOf(
            OnBoardingOneFragment(),
            OnBoardingTwoFragment(),
            OnBoardingThreeFragment()
        )
        adapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragments)
        viewPager.adapter = adapter
    }

    private fun setupIndicators() {
        val indicatorLayout = binding.indicatorLayout
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i]?.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_indicator_not_active
                    )
                )
                this.layoutParams = layoutParams
                this.setOnClickListener {
                    viewPager.currentItem = i
                }
            }
            indicatorLayout.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val indicatorLayout = binding.indicatorLayout
        val childCount = indicatorLayout.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.shape_indicator_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_indicator_not_active
                    )
                )
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}