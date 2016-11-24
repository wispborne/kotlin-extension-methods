/*
 * Copyright (C) 2014 Jared Rummler <jared.rummler@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thunderclouddev.changelogs.ui.utils

import android.app.Activity
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.UiThread
import android.view.*
import android.widget.ImageView
import com.thunderclouddev.changelogs.R
import java.util.*

/**
 * Helper class to set the color and transparency for menu icons in an ActionBar/Toolbar.
 *
 *
 * Example usage:
 *
 *
 *
 * `
 * public boolean onCreateOptionsMenu(Menu menu) {
 * ...
 * int color = getResources().getColor(R.color.your_awesome_color);
 * int alpha = 204; // 80% alpha
 * MenuColorizer.colorMenu(this, menu, color, alpha);
 * ...
 * }
` *
 *

 * @author Jared Rummler @gmail.com>
 * *
 * @since Dec 11, 2014
 */
object MenuColorizer {

    /**
     * Sets a color filter on all menu icons, including the overflow button (if it exists)
     */
    @UiThread
    @JvmOverloads fun colorMenu(activity: Activity, menu: Menu, @ColorInt color: Int,
                                alpha: Int = 0) {
        for (i in 0..menu.size() - 1) {
            val menuItem = menu.getItem(i)
            colorMenuItem(menuItem, color, alpha)

            if (menuItem.hasSubMenu()) {
                val subMenu = menuItem.subMenu

                for (j in 0..subMenu.size() - 1) {
                    colorMenuItem(subMenu.getItem(j), color, alpha)
                }
            }
        }

        val filter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
        activity.runOnUiThread { setOverflowButtonColor(activity, filter, alpha) }
    }

    /**
     * Sets a color filter on a [MenuItem]
     */
    @UiThread
    @JvmOverloads fun colorMenuItem(menuItem: MenuItem, @ColorInt color: Int, alpha: Int = 0) {
        val drawable = menuItem.icon
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawables with this id will have a color
            // filter applied to it.
            drawable.mutate()
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            if (alpha > 0) {
                drawable.alpha = alpha
            }
        }
    }

    /**
     * It's important to set overflowDescription attribute in styles, so we can grab the reference
     * to the overflow icon. Check: res/values/styles.xml
     *
     *

     * @param activity
     * *
     * @param colorFilter
     * *
     * @see [Source](https://snowdog.co/blog/how-to-dynamicaly-change-android-toolbar-icons-color/)
     */
    @UiThread
    private fun setOverflowButtonColor(activity: Activity,
                                       colorFilter: PorterDuffColorFilter, alpha: Int) {
        val overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description)
        val decorView = activity.window.decorView as ViewGroup
        val viewTreeObserver = decorView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val outViews = ArrayList<View>()
                decorView.findViewsWithText(outViews, overflowDescription,
                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)

                if (outViews.isEmpty()) {
                    return
                }

                val overflow = outViews[0] as ImageView
                overflow.colorFilter = colorFilter

                if (alpha > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        overflow.imageAlpha = alpha
                    } else {
                        overflow.setAlpha(alpha)
                    }
                }

                removeOnGlobalLayoutListener(decorView, this)
            }
        })
    }

    private fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.viewTreeObserver.removeGlobalOnLayoutListener(listener)
        } else {
            v.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}
/**
 * Sets a color filter on all menu icons, including the overflow button (if it exists)
 */
/**
 * Sets a color filter on a [MenuItem]
 */