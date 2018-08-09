package com.app.findmycafeplus.Interface

import com.app.findmycafeplus.Model.RMCafeInformation
import io.realm.RealmResults

interface FilterDialogCallBackInterface {

    fun filterResult(result : RealmResults<RMCafeInformation>)
}