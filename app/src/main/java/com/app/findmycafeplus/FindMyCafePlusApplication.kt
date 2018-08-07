package com.app.findmycafeplus

import android.app.Application
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.Manager.RealManager

class FindMyCafePlusApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        RealManager.initRealm(this)
        RealManager.initConfiguration()

        DataManager.initData(this)
    }
}