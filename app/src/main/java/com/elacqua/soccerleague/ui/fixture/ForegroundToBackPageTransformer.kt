package com.elacqua.soccerleague.ui.fixture

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ForegroundToBackPageTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val height = page.height.toFloat()
        val width = page.width.toFloat()
        val scale = min(if (position > 0) 1f else abs(1f + position), 0.5f)
        page.apply {
            scaleX = scale
            scaleY = scale
            pivotX = width * 0.5f
            pivotY = height * 0.5f
            translationX = if (position > 0) width * position else -width * position * 0.25f
        }

    }

    private fun min(value: Float, min: Float): Float {
        return if (value < min) min else value
    }
}