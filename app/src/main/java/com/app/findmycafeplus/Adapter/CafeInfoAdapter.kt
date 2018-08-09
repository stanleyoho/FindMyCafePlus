package com.app.findmycafeplus.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.layout_cafe_info_window.view.*

class CafeInfoAdapter(context: Context?) : GoogleMap.InfoWindowAdapter{

    var mContext : Context? = context

    override fun getInfoContents(p0: Marker?): View {
        return getInfoWindow(p0)
    }

    override fun getInfoWindow(p0: Marker?): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_cafe_info_window,null)

        if (p0?.tag is RMCafeInformation) {
            val marker = p0.tag as RMCafeInformation
            view.textTitle.text = marker.name
            view.ratingStarWifi.drawStars(marker.wifi.toFloat())
            view.ratingStarQuite.drawStars(marker.quiet.toFloat())
            view.ratingStarSeat.drawStars(marker.seat.toFloat())
            view.ratingStarCheap.drawStars(marker.cheap.toFloat())
            view.ratingStarTasty.drawStars(marker.tasty.toFloat())
        }
        return view
    }
}