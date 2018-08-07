package com.app.findmycafeplus.Interface

import org.json.JSONArray
import org.json.JSONObject

interface ApiCallBackInterface {

    fun onResponse(tag : Int , response : JSONObject)

    fun onResponse(tag : Int , response : JSONArray)

    fun onErrorResponse(tag : Int , errorMessage : String)
}