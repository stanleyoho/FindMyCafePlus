package com.app.findmycafeplus.Utils

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast

object Utils {

    /**
     * 計算經緯度距離
     * */
    fun GetDistance(lat1 : Double , lng1 : Double, lat2 : Double , lng2 : Double ): Double{
        val EARTH_RADIUS = 6378137
        val radLat1 = rad(lat1)
        val radLat2 = rad(lat2)
        val a = radLat1 - radLat2
        val b = rad(lng1) - rad(lng2)
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2.0) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2.0)))
        s = s * EARTH_RADIUS
        s = (Math.round(s * 10000) / 10000).toDouble()
        return s
    }

    private fun rad(d : Double ) : Double{
        return d * Math.PI / 180.0
    }


    /**
     * 設定全版Dialog
     * */
    fun setFullScreenDialog(dialog : Dialog){
        try{
            val metrics = DisplayMetrics()
            dialog.window.windowManager.defaultDisplay.getMetrics(metrics)

            val window = dialog.window
            window.decorView.setPadding(0,0,0,0)
            val lp = window.attributes
            lp.width = metrics.widthPixels
            lp.height = WindowManager.LayoutParams.MATCH_PARENT

            window.attributes = lp

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE )

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    fun px2Dp(context : Context , px : Float) : Int{
        val density = context.resources.displayMetrics.density
        return (px / density + 0.5f).toInt()
    }

    fun dp2Px(context : Context , dp : Float) : Int{
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    fun showToast(context: Context , message : String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}