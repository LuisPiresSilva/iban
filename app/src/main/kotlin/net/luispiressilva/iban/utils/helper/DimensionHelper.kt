package net.luispiressilva.iban.utils.helper

import android.app.Activity
import android.content.Context
import android.graphics.Point
import androidx.fragment.app.FragmentActivity

class DimensionHelper(private val ctx : Context) {
    companion object {
        fun isTablet(context: Context?): Boolean {
            return false
//            return context?.resources?.getBoolean(R.bool.isTablet) ?: false
        }

        fun getDisplayWidth(activity: Activity): Int {
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)

            return size.x
        }

        fun getDisplayHeight(activity: Activity): Int {
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)

            return size.y
        }

        fun getDisplayWidth(activity: FragmentActivity): Int {
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)

            return size.x
        }

        fun getDisplayHeight(activity: FragmentActivity): Int {
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)

            return size.y
        }
    }


    fun returnTabletOrPhone(first : Any?, second : Any?) = if (isTablet(ctx)) first else second

}