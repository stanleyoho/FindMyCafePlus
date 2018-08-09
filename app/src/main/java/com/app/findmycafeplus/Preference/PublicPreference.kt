package com.app.findmycafeplus.Preference

import android.content.Context
import android.content.SharedPreferences
import com.app.findmycafeplus.Constants.Constants

class PublicPreference {

    private var preference : SharedPreferences
    private var edit : SharedPreferences.Editor

    constructor(context : Context){
        preference = context.getSharedPreferences(Constants.PUBLIC_PREFERENCE,Context.MODE_PRIVATE)
        edit = preference.edit()
    }


    fun setWifi(value : Float){
        edit.putFloat(Constants.PUBLIC_WIFI,value)
        edit.apply()
    }

    fun getWifi() : Float{
        return preference.getFloat(Constants.PUBLIC_WIFI,0f)
    }

    fun setSeat(value : Float){
        edit.putFloat(Constants.PUBLIC_SEAT,value)
        edit.apply()
    }

    fun getSeat() : Float{
        return preference.getFloat(Constants.PUBLIC_SEAT,0f)
    }

    fun setQuite(value : Float){
        edit.putFloat(Constants.PUBLIC_QUITE,value)
        edit.apply()
    }

    fun getQuite() : Float{
        return preference.getFloat(Constants.PUBLIC_QUITE,0f)
    }

    fun setCheap(value : Float){
        edit.putFloat(Constants.PUBLIC_CHEAP,value)
        edit.apply()
    }

    fun getCheap() : Float{
        return preference.getFloat(Constants.PUBLIC_CHEAP,0f)
    }

    fun setTasty(value : Float){
        edit.putFloat(Constants.PUBLIC_TASTY,value)
        edit.apply()
    }

    fun getTasty() : Float{
        return preference.getFloat(Constants.PUBLIC_TASTY,0f)
    }

    fun setLine(value : Int){
        edit.putInt(Constants.PUBLIC_LINE,value)
        edit.apply()
    }

    fun getLine() : Int{
        return preference.getInt(Constants.PUBLIC_LINE,0)
    }

    fun setStation(value : Int){
        edit.putInt(Constants.PUBLIC_STATION,value)
        edit.apply()
    }

    fun getStation() : Int{
        return preference.getInt(Constants.PUBLIC_STATION,0)
    }
}