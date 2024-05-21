package com.solutionplus.altasherat.common.presentation.coustom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.solutionplus.altasherat.R
import com.solutionplus.altasherat.databinding.CustomButtonBinding

class CustomButtonOnBoarding @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: CustomButtonBinding =
        CustomButtonBinding.inflate(LayoutInflater.from(context), this, true)
    private val imageView: ImageView = binding.buttonImage
    private val textView: TextView = binding.buttonText

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CustomButtonOnBoarding, 0, 0)
            val text = typedArray.getString(R.styleable.CustomButtonOnBoarding_customText)
            val imageResId = typedArray.getResourceId(R.styleable.CustomButtonOnBoarding_customImage, 0)

            textView.text = text
            if (imageResId != 0) {
                imageView.setImageResource(imageResId)
            }
            typedArray.recycle()
        }
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setImageResource(resId: Int) {
        imageView.setImageResource(resId)
    }
}