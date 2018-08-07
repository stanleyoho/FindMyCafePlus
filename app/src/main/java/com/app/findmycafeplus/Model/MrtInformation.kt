package com.app.findmycafeplus.Model

import java.io.Serializable

data class MrtInformation (val station_name_english : String ,
                           val station_id : String,
                           val station_address : String,
                           val station_name_chinese : String,
                           val station_line_id : String,
                           val station_line_name : String,
                           val station_location_code : Int,
                           val latitude : Double,
                           val longitude : Double): Serializable {

}