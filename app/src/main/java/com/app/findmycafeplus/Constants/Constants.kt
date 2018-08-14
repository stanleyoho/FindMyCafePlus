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

    /** Filter Preference  */
    const val FILTER_PREFERENCE = "FILTER_PREFERENCE"
    const val FILTER_PREFERENCE_WIFI = "FILTER_PREFERENCE_WIFI"
    const val FILTER_PREFERENCE_QUITE = "FILTER_PREFERENCE_QUITE"
    const val FILTER_PREFERENCE_SEAT = "FILTER_PREFERENCE_SEAT"
    const val FILTER_PREFERENCE_CHEAP = "FILTER_PREFERENCE_CHEAP"
    const val FILTER_PREFERENCE_TASTY = "FILTER_PREFERENCE_TASTY"
    const val FILTER_PREFERENCE_LINE = "FILTER_PREFERENCE_LINE"
    const val FILTER_PREFERENCE_STATION = "FILTER_PREFERENCE_STATION"

    /** User Preference  */
    const val USER_PREFERENCE = "USER_PREFERENCE"
    const val USER_PREFERENCE_TOKEN = "USER_PREFERENCE_TOKEN"
    const val USER_PREFERENCE_EMAIL = "USER_PREFERENCE_EMAIL"
    const val USER_PREFERENCE_IMAGE_URL = "USER_PREFERENCE_IMAGE_URL"
    const val USER_PREFERENCE_LOGIN_TYPE = "USER_PREFERENCE_LOGIN_TYPE"

    /** camera zoom*/
    const val ZOOM_MAX : Float = 25f
    const val ZOOM_MIN : Float = 10f
    const val ZOOM_NORMAL : Float = 18f

    /** login type*/
    const val LOGIN_TYPE_GOOGLE : Int = 0
    const val LOGIN_TYPE_FACEBOOK : Int = 1
    const val LOGIN_TYPE_EMAIL : Int = 2
}