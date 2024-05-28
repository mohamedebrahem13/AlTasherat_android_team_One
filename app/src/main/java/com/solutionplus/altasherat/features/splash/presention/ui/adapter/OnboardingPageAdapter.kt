package com.solutionplus.altasherat.features.splash.presention.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.databinding.ItemOnboardingPageBinding
import com.solutionplus.altasherat.features.splash.presention.ui.fragments.OnboardingPage

class OnboardingPageAdapter(
    private val context: Context,
    private val pages: List<OnboardingPage>
) : RecyclerView.Adapter<OnboardingPageAdapter.OnboardingPageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingPageViewHolder {
        val binding = ItemOnboardingPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnboardingPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingPageViewHolder, position: Int) {
        holder.bind(context, pages[position])
    }

    override fun getItemCount(): Int = pages.size

    inner class OnboardingPageViewHolder(private val binding: ItemOnboardingPageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, page: OnboardingPage) {
            binding.imageStamp.setImageResource(page.imageResId)
            binding.card.textWelcome2.text = context.getString(page.description)
        }
    }
}