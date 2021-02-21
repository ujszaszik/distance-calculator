package com.udacity.distancecalculator.screens.mapselector.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.udacity.distancecalculator.R
import com.udacity.distancecalculator.common.text.firstLetterOrEmpty
import com.udacity.distancecalculator.databinding.MapSelectorBottomRowBinding

class MapSelectorBottomRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: MapSelectorBottomRowBinding

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = MapSelectorBottomRowBinding.inflate(layoutInflater, this, true)

        val array = context.obtainStyledAttributes(attrs, R.styleable.MapSelectorBottomRow)
        binding.circleView.background =
            array.getDrawable(R.styleable.MapSelectorBottomRow_circleBackground)
        binding.circleView.text =
            array.getString(R.styleable.MapSelectorBottomRow_circleLetter).firstLetterOrEmpty()
        binding.labelText.text =
            array.getString(R.styleable.MapSelectorBottomRow_labelText)
        binding.valueText.text =
            array.getString(R.styleable.MapSelectorBottomRow_valueText)
        binding.cancelView.isVisible =
            array.getBoolean(R.styleable.MapSelectorBottomRow_isIconVisible, false)

        array.recycle()

        hide()
    }

    fun setValueText(text: String) {
        binding.valueText.text = text
    }

    fun setIconVisibility(value: Boolean) {
        binding.cancelView.isVisible = value
    }

    fun setIconClickListener(block: () -> Unit) {
        binding.cancelView.setOnClickListener { block.invoke() }
    }

    fun hide() {
        binding.bottomRowLayout.visibility = View.GONE
    }

    fun show() {
        binding.bottomRowLayout.visibility = View.VISIBLE
    }
}