package com.app.findmycafeplus.Utils

import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng

object MapUtils {

    fun getCameraLatLngZoom(latitude: Double, longitude: Double, zoom: Int): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom.toFloat())
    }

    fun getCameraScrollBy(x: Float, y: Float): CameraUpdate {
        return CameraUpdateFactory.scrollBy(x, y)
    }
}