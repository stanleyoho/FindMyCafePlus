package com.app.findmycafeplus.Utils

import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.R.id.map
import com.app.findmycafeplus.R.id.name
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

object MapUtils {

    /**
     * 統一取得map camera
     * */
    private fun getCameraLatLngZoom(latitude: Double, longitude: Double, zoom: Float): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude), zoom)
    }

    /**
     * 以目前位置為基準移動
     * @param x 往右移動
     * @param y 往下移動
     * */
    fun getCameraScrollBy(x: Float, y: Float): CameraUpdate {
        return CameraUpdateFactory.scrollBy(x, y)
    }

    /**
     * move camera by location
     * */
    fun moveCameraTo(map : GoogleMap , location : Location? , zoom : Float){
        if(location == null){
            moveCameraToTaipei(map,zoom)
        }else{
            map.animateCamera(getCameraLatLngZoom(location.latitude,location.longitude,zoom))
        }
    }

    /**
     * move camera by lat,log
     * */
    fun moveCameraTo(map : GoogleMap , lat : Double , lon : Double , zoom : Float){
        map.animateCamera(getCameraLatLngZoom(lat,lon,zoom))
    }

    /**
     * move camera to taipei main station
     * */
    fun moveCameraToTaipei(map : GoogleMap , zoom : Float){
        map.animateCamera(getCameraLatLngZoom(Constants.LOCATION_TAIPIE_STATION_LAT,Constants.LOCATION_TAIPIE_STATION_LOG,zoom))
    }

    /**
     * get google map Intent
     * */
    fun getGoogleMapIntent(context : Context ,cafeInfo : RMCafeInformation){
        val mapLocationString = "geo:${cafeInfo.latitude},${cafeInfo.longitude}?z=10&q=${cafeInfo.name}"
        LogUtils.e("google map intent string",mapLocationString)
        val mapIntent = Intent(Intent.ACTION_VIEW,Uri.parse(mapLocationString))
        mapIntent.setPackage("com.google.android.apps.maps")
        if(mapIntent.resolveActivity(context.packageManager) != null){
            context.startActivity(mapIntent)
        }
    }
}