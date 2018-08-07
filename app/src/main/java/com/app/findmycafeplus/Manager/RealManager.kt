package com.app.findmycafeplus.Manager

import android.content.Context
import com.app.findmycafeplus.Constants.Constants
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration

object RealManager {

    fun initRealm(context : Context){
        Realm.init(context)
    }

    fun initConfiguration(){

        val config = RealmConfiguration.Builder()
                .name(Constants.REALM_DB_NAME)
                .schemaVersion(Constants.REALM_VERSION)
                .build()

        Realm.setDefaultConfiguration(config)
    }

    fun getRealm() : Realm{
        return Realm.getDefaultInstance()
    }

    /** realm migration 更新版本  */
//    class CafeTravelerMigration : RealmMigration {
//        override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
//
//        }
//
//        override fun hashCode(): Int {
//            return 37
//        }
//
//        override fun equals(o: Any?): Boolean {
//            return o is CafeTravelerMigration
//        }
//    }
}