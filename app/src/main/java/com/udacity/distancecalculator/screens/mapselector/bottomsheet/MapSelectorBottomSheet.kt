package com.udacity.distancecalculator.screens.mapselector.bottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.distancecalculator.common.view.getVisibleHeight
import com.udacity.distancecalculator.databinding.MapSelectorBottomSheetBinding


class MapSelectorBottomSheet @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: MapSelectorBottomSheetBinding

    private val _visibleHeight = MutableLiveData<Int>()
    val visibleHeight: LiveData<Int> get() = _visibleHeight

    init {
        val inflater = LayoutInflater.from(context)
        binding = MapSelectorBottomSheetBinding.inflate(inflater, this, true)
        adjust(MapSelectorSheetState.EMPTY)
        addVisibleHeightObserver()
    }

    private fun addVisibleHeightObserver() {
        val vto: ViewTreeObserver = viewTreeObserver
        vto.addOnGlobalLayoutListener { _visibleHeight.postValue(getVisibleHeight()) }
    }

    private fun setStartingPointText(text: String) {
        binding.fromSelectionRow.setValueText(text)
    }

    private fun setStartingPointIconVisibility(value: Boolean) {
        binding.fromSelectionRow.setIconVisibility(value)
    }

    private fun setDestinationText(text: String) {
        binding.toSelectionRow.setValueText(text)
    }

    private fun setDestinationIconVisibility(value: Boolean) {
        binding.toSelectionRow.setIconVisibility(value)
    }

    fun setButtonClickListener(block: () -> Unit) {
        binding.mapCalculateButton.setOnClickListener { block.invoke() }
    }

    fun setButtonVisibility(value: Boolean) {
        binding.mapCalculateButton.isVisible = value
        binding.mapCalculateButton.isClickable = value
    }

    fun setStartingPointIconListener(block: () -> Unit) {
        binding.fromSelectionRow.setIconClickListener(block)
    }

    fun setDestinationIconListener(block: () -> Unit) {
        binding.toSelectionRow.setIconClickListener(block)
    }

    fun startingPointToEnabledWithText(text: String) {
        setStartingPointIconVisibility(true)
        setStartingPointText(text)
        binding.fromSelectionRow.show()
    }

    fun startingPointToDisabled() {
        setStartingPointIconVisibility(false)
        setStartingPointText("")
        binding.fromSelectionRow.hide()
    }

    fun destinationToEnabledWithText(text: String) {
        setDestinationIconVisibility(true)
        setDestinationText(text)
        binding.toSelectionRow.show()
    }

    fun destinationToDisabled() {
        setDestinationIconVisibility(false)
        setDestinationText("")
        binding.toSelectionRow.hide()
    }

    fun adjust(state: MapSelectorSheetState) {
        maxHeight = state.getHeight()
    }

}