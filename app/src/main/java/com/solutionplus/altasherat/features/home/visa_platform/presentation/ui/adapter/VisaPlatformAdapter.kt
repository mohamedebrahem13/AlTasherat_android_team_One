package com.solutionplus.altasherat.features.home.visa_platform.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutionplus.altasherat.databinding.ItemVisaPlatformBinding
import com.solutionplus.altasherat.databinding.ItemVisaPlatformHeaderBinding

class VisaPlatformAdapter(
    private val items: List<VisaPlatformItem>,
    private val visaPlatformCallback: VisaPlatformCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        when (viewType) {
            HEADER_ITEM -> {
                val binding = ItemVisaPlatformHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolderHeader(binding)
            }

            NORMAL_ITEM -> {
                val binding = ItemVisaPlatformBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }

            else -> throw Exception("View type not supported...")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HEADER_ITEM -> (holder as ViewHolderHeader).bind()
            NORMAL_ITEM -> (holder as ViewHolder).bind(items[position - 1])
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_ITEM
            else -> NORMAL_ITEM
        }
    }

    inner class ViewHolder(val binding: ItemVisaPlatformBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonVisa.setOnClickListener {
                visaPlatformCallback.onVisaItemClicked(items[bindingAdapterPosition - 1].type)
            }
        }

        fun bind(item: VisaPlatformItem) {
            with(binding) {
                textTitleFirstPart.text = root.context.getString(item.titleFirstPart)
                textTitleSecondPart.text = root.context.getString(item.titleSecondPart)
                buttonVisa.text = root.context.getString(item.buttonText)
                textVisa.text = root.context.getString(item.actionText)
                layoutVisa.setBackgroundResource(item.image)
            }
        }
    }

    inner class ViewHolderHeader(val binding: ItemVisaPlatformHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    companion object {
        const val HEADER_ITEM = 0
        const val NORMAL_ITEM = 1
    }
}