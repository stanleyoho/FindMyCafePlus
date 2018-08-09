package com.app.findmycafeplus.Model

import com.app.findmycafeplus.Manager.RealManager
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable


open class RMCafeInformation : RealmObject() {

    @PrimaryKey
    private var id: String? = null

    //about coffee shop variable
    var name: String? = ""
    var city: String? = ""
    var wifi: Float = 0f
    var seat: Float = 0f
    var quiet: Float = 0f
    var tasty: Float = 0f
    var cheap: Float = 0f
    var music: Double = 0.0
    var url: String? = ""
    var address: String? = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0

    var limited_time: String? = ""
    var socket: String? = ""
    var standing_desk: String? = ""
    var mrt: String? = ""
    var open_time: String? = ""

    //about mrt variable
    var nearestStationName: String? = ""
    var nearestAnnotation: String? = ""
    var isRedLine: Boolean = false
    var isGreenLine: Boolean = false
    var isBlueLine: Boolean = false
    var isOrangeLine: Boolean = false
    var isBrownLine: Boolean = false

    public fun restLines(){
        isBlueLine = false
        isRedLine = false
        isOrangeLine = false
        isGreenLine = false
        isBrownLine = false
    }

    public fun setLineType(line: String) {
        when (line) {
            "BR"    -> isBrownLine = true
            "G"     -> isGreenLine = true
            "BL"    -> isBlueLine = true
            "R"     -> isRedLine = true
            "O"     -> isOrangeLine = true
        }
    }

    /**
     * 一次新增多筆訊息
     */
    fun addAll(cafeList: ArrayList<RMCafeInformation>) {
        val realm = RealManager.getRealm()
        realm.executeTransaction {
            it.insertOrUpdate(cafeList)
        }
    }

    /**
     * 刪除全部訊息
     */
    fun deleteAll() {
        val realm = RealManager.getRealm()
        val result = realm.where(RMCafeInformation::class.java).findAll()
        realm.executeTransaction {
            result.deleteAllFromRealm()
        }
    }

    /**
     * 取回 全部訊息
     */
    fun getAll(): RealmResults<RMCafeInformation> {
        val realm = RealManager.getRealm()

        return realm.where(RMCafeInformation::class.java).findAll()
    }
}