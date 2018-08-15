package com.app.findmycafeplus.Preference

import android.content.Context
import android.content.SharedPreferences
import com.app.findmycafeplus.Constants.Constants

class UserPreference {

    private var preference : SharedPreferences
    private var edit : SharedPreferences.Editor

    var loginType : Int
    get(){
        return preference.getInt(Constants.USER_PREFERENCE_LOGIN_TYPE,-1)
    }
    set(value) {
        edit.putInt(Constants.USER_PREFERENCE_LOGIN_TYPE,value)
        edit.apply()
    }

    constructor(context : Context){
        preference = context.getSharedPreferences(Constants.USER_PREFERENCE, Context.MODE_PRIVATE)
        edit = preference.edit()
    }

}