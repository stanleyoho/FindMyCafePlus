package com.app.findmycafeplus.Model

import java.io.Serializable

data class CafeInformation (
        val id : String,
        val name : String,
        val city : String,
        val wifi : Double,
        val seat : Double,
        val quiet : Double,
        val tasty : Double,
        val cheap : Double,
        val music : Double,
        val url : String,
        val address : String,
        val latitude : Double,
        val longitude : Double,
        val limited_time : String,
        val socket : String,
        val standing_desk : String,
        val mrt : String,
        val open_time : String): Serializable{
}