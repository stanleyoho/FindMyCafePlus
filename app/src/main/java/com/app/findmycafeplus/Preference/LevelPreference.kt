package com.app.findmycafeplus.Preference

import android.content.Context
import android.content.SharedPreferences
import com.app.findmycafeplus.Constants.Constants

class LevelPreference {

    private var preference : SharedPreferences
    private var edit : SharedPreferences.Editor

    var level : Int
    get() {
        return preference.getInt(Constants.LEVEL_PREFERENCE_LEVEL,0)
    }
    set(value) {
        edit.putInt(Constants.LEVEL_PREFERENCE_LEVEL,value)
        edit.apply()
    }

    var experience : Int
    get() {
        return preference.getInt(Constants.LEVEL_PREFERENCE_XP,0)
    }
    set(value) {
        edit.putInt(Constants.LEVEL_PREFERENCE_XP,value)
        edit.apply()
    }

    constructor(context: Context){
        preference = context.getSharedPreferences(Constants.LEVEL_PREFERENCE, Context.MODE_PRIVATE)
        edit = preference.edit()
    }

    fun addLevelChangeListener(listener : SharedPreferences.OnSharedPreferenceChangeListener) {
        preference.registerOnSharedPreferenceChangeListener(listener)
    }

    fun removeLevelChangeListener(listener : SharedPreferences.OnSharedPreferenceChangeListener){
        preference.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun reset(){
        level = 0
        experience = 0
    }
}
