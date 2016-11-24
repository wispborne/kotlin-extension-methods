package com.thunderclouddev.changelogs.ui.utils

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.v4.graphics.drawable.DrawableCompat

/**
 * Created by David Whitman
 */
object TintUtils {

    /**
     * Uses compatibility library to create a tinted a drawable. Supports all important versions of Android.

     * @param drawable Drawable to tint
     * *
     * @param color    Color to tint to, *not* R.color.whatever, must be resolved
     * *
     * @param mode     Mode of tinting to use
     * *
     * @return A tinted Drawable. Wraps the Drawable in a new class - instanceof will NOT match the old type
     */
    @JvmOverloads fun createTintedDrawable(drawable: Drawable?, @ColorInt color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN): Drawable? {
        if (drawable == null) {
            return null
        }

        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrappedDrawable, color)
        DrawableCompat.setTintMode(wrappedDrawable, mode)
        return wrappedDrawable
    }

    /**
     * Uses compatibility library to create a tinted a drawable. Supports all important versions of Android.

     * @param drawable Drawable to tint
     * *
     * @param color    Color to tint to
     * *
     * @param mode     Mode of tinting to use
     * *
     * @return A tinted Drawable. Wraps the Drawable in a new class - instanceof will NOT match the old type
     */
    fun createTintedDrawable(drawable: Drawable?, color: ColorStateList, mode: PorterDuff.Mode): Drawable? {
        if (drawable == null) {
            return null
        }

        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, color)
        DrawableCompat.setTintMode(wrappedDrawable, mode)
        return wrappedDrawable
    }
}