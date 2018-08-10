package com.app.findmycafeplus.Preference

import android.content.Context
import android.content.SharedPreferences
import com.app.findmycafeplus.Constants.Constants

class UserPreferenct {

    private var preference : SharedPreferences
    private var edit : SharedPreferences.Editor

    constructor(context : Context){
        preference = context.getSharedPreferences(Constants.FILTER_PREFERENCE, Context.MODE_PRIVATE)
        edit = preference.edit()
    }
}