package com.app.findmycafeplus.Manager

import com.app.findmycafeplus.Model.CafeNews
import com.app.findmycafeplus.Utils.LogUtils
import com.google.firebase.database.*

object RealtimeDatabaseManager {

    private lateinit var dataBase : FirebaseDatabase
    lateinit var newsReference : DatabaseReference
    private lateinit var newList : ArrayList<CafeNews>

    fun init(){
        dataBase = FirebaseDatabase.getInstance()
        newsReference = dataBase.getReference("news")
    }
}