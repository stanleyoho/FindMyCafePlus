package com.app.findmycafeplus

import android.app.Application
import android.os.Handler
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.Manager.RealManager
import com.app.findmycafeplus.Manager.RealtimeDatabaseManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.iid.FirebaseInstanceId

class FindMyCafePlusApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        FacebookSdk.sdkInitialize(this)
        AppEventsLogger.activateApp(this)
        FirebaseInstanceId.getInstance().instanceId

        RealManager.initRealm(this)
        RealManager.initConfiguration()
        RealtimeDatabaseManager.init()

//        DataManager.initData(this)

        val run = Runnable { DataManager.initData(applicationContext) }

        Handler().post(run)
    }
}