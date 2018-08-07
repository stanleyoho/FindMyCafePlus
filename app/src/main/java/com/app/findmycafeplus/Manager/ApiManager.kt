package com.app.findmycafeplus.Manager

import com.app.findmycafeplus.Interface.ApiCallBackInterface
import com.app.findmycafeplus.Constants.UrlConstants
import com.app.findmycafeplus.Utils.OkhttpUtils

object ApiManager {




    private fun getUrl(url : String) : String{
        return  UrlConstants.HTTP + UrlConstants.CAFE_INFO_HOST+url
    }


    fun apiGetTaipeiCafeInformation(tag : Int , callBack : ApiCallBackInterface){
        OkhttpUtils.get(tag, getUrl(UrlConstants.CAFE_INFO_TAIPEI),callBack)

    }
}