package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.databinding.ItemVisaPlatformBinding

class VisaPlatformAdapter(
    private val items: List<VisaItem>
) : RecyclerView.Adapter<VisaPlatformAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VisaPlatformAdapter.ViewHolder {
        val binding =
            ItemVisaPlatformBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisaPlatformAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> FIRST_ITEM
            else -> VISA_WITH_BUTTON
        }
    }

    inner class ViewHolder(val binding: ItemVisaPlatformBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VisaItem) {
            with(binding) {
                textTitleFirstPart.text = root.context.getString(item.titleFirstPart)
                textTitleSecondPart.text = root.context.getString(item.titleSecondPart)
                buttonVisa.text = root.context.getString(item.buttonText)
                textVisa.text = root.context.getString(item.actionText)
                layoutVisa.setBackgroundResource(item.image)
            }
        }
    }

    companion object {
        const val FIRST_ITEM = 0
        const val VISA_WITH_BUTTON = 1
    }
}