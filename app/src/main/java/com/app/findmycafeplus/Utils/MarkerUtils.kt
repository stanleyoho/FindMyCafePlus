package com.app.findmycafeplus.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v4.content.ContextCompat
import com.app.findmycafeplus.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

object MarkerUtils {

    fun getMapCheckedIcon(context : Context): BitmapDescriptor {

        val icon  =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.resources.getDrawable(R.drawable.ic_location_checked, null) as BitmapDrawable
                } else {
                    ContextCompat.getDrawable(context, R.drawable.ic_location_checked) as BitmapDrawable
                }

        val bitmap = Bitmap.createScaledBitmap(icon.bitmap,
                Utils.px2Dp(context,250f),
                Utils.px2Dp(context,300f),
                false)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    fun getMapNormalIcon(context : Context): BitmapDescriptor {
        val icon =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.resources.getDrawable(R.drawable.ic_location_normal, null) as BitmapDrawable
                } else {
                    ContextCompat.getDrawable(context, R.drawable.ic_location_normal) as BitmapDrawable
                }

        val bitmap = Bitmap.createScaledBitmap(icon.bitmap,
                Utils.px2Dp(context,250f),
                Utils.px2Dp(context,250f),
                false)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}