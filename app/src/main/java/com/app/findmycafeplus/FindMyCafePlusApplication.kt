package com.app.findmycafeplus

import android.app.Application
import android.os.Handler
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.Manager.RealManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class FindMyCafePlusApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)

        RealManager.initRealm(this)
        RealManager.initConfiguration()


//        DataManager.initData(this)

        val run = Runnable { DataManager.initData(applicationContext) }

        Handler().post(run)
    }
}