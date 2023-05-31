package com.ennovations.fitcrucoach.quickbox.utils

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.*
import androidx.annotation.IntRange
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.utility.SessionManager


private const val RANDOM_COLOR_START_RANGE = 0
private const val RANDOM_COLOR_END_RANGE = 9

fun getColorCircleDrawable(colorPosition: Int): Drawable {
    return getColoredCircleDrawable(getCircleColor(colorPosition % RANDOM_COLOR_END_RANGE))
}

fun getColoredCircleDrawable(@ColorInt color: Int): Drawable {
    val drawable = getDrawable(R.drawable.shape_circle) as GradientDrawable
    drawable.setColor(color)
    return drawable
}

fun getCircleColor(
    @IntRange(from = RANDOM_COLOR_START_RANGE.toLong(), to = RANDOM_COLOR_END_RANGE.toLong())
    colorPosition: Int
): Int {
    val colorIdName = String.format("random_color_%d", colorPosition + 1)
    val colorId = SessionManager.getInstance().resources
        .getIdentifier(colorIdName, "color", SessionManager.getInstance().packageName)

    return getColor(colorId)
}

fun getString(@StringRes stringId: Int): String {
    return SessionManager.getInstance().getString(stringId)
}

fun getDrawable(@DrawableRes drawableId: Int): Drawable {
    return SessionManager.getInstance().resources.getDrawable(drawableId)
}

fun getColor(@ColorRes colorId: Int): Int {
    return SessionManager.getInstance().resources.getColor(colorId)
}

fun getDimen(@DimenRes dimenId: Int): Int {
    return SessionManager.getInstance().resources.getDimension(dimenId).toInt()
}