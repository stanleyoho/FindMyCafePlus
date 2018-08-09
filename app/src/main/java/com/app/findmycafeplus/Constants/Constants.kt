package com.app.findmycafeplus.Constants

object Constants {

    /** Permission request code*/
    const val PERMISSION_CODE : Int = 200

    /** Default Location  */
    const val LOCATION_TAIPIE_STATION_LAT = 25.048734
    const val LOCATION_TAIPIE_STATION_LOG = 121.514231

    /** realm db name  */
    const val REALM_DB_NAME = "FindMyCafe.realm"
    /** realm db version  */
    const val REALM_VERSION: Long = 1

    /** Mrt Line  */
    const val LINE_RED = 1
    const val LINE_BROWN = 2
    const val LINE_GREEN = 3
    const val LINE_ORANGE = 4
    const val LINE_BLUE = 5

    /** Preference  */
    const val PUBLIC_PREFERENCE = "PUBLIC_PREFERENCE"
    const val PUBLIC_WIFI = "PUBLIC_PREFERENCE_WIFI"
    const val PUBLIC_QUITE = "PUBLIC_PREFERENCE_QUITE"
    const val PUBLIC_SEAT = "PUBLIC_PREFERENCE_SEAT"
    const val PUBLIC_CHEAP = "PUBLIC_PREFERENCE_CHEAP"
    const val PUBLIC_TASTY = "PUBLIC_PREFERENCE_TASTY"

    const val PUBLIC_LINE = "PUBLIC_PREFERENCE_LINE"
    const val PUBLIC_STATION = "PUBLIC_PREFERENCE_STATION"
}