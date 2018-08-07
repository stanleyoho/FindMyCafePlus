package com.app.findmycafeplus.Utils

import com.app.findmycafeplus.Interface.ApiCallBackInterface
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

object OkhttpUtils {

    private val mOkhttpClient : OkHttpClient?
    private const val TIME_OUT_SECOND : Long = 3 * 1000
    private const val CONTENT_TYPE : String = "application/json"

    init {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT_SECOND,TimeUnit.SECONDS)
        mOkhttpClient =builder.build()
    }

    /**
     *  get request log
     * */
    fun get(tag : Int , url : String , apiCallBack : ApiCallBackInterface){
        LogUtils.d("/**********************************************/")
        LogUtils.d("OkHttp Get")
        LogUtils.d("OkHttp Tag : $tag")
        LogUtils.d("OkHttp url : $url")
        LogUtils.d("/**********************************************/")
        get(tag, url,buildGetRequest(url),apiCallBack)
    }

    private fun get(tag : Int , url : String ,request : Request, apiCallBack : ApiCallBackInterface){
        mOkhttpClient?.newCall(request)?.enqueue(OkHttpCallBack(tag,url,apiCallBack))
    }

    private fun buildGetRequest(url : String) : Request{

        return Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", CONTENT_TYPE)
                .build()
    }

    class OkHttpCallBack(tag : Int , url : String , callBack : ApiCallBackInterface) : Callback{
        private val tag : Int = tag
        private val url : String = url
        private val callback : ApiCallBackInterface = callBack
        private val errorCode : String = "Server Erroc"

        override fun onResponse(call: okhttp3.Call?, response: Response?) {
            try{
                val responseString = response?.body()?.string()
                LogUtils.d("/**********************************************/")
                LogUtils.d("OkHttp Tag : $tag")
                LogUtils.d("OkHttp url : $url")
                LogUtils.d("OkHttp response code : " + response?.code())
                LogUtils.d("OkHttp response body : $responseString")
                LogUtils.d("/**********************************************/")

                val parser = JsonParser().parse(responseString)

                when (parser) {
                    is JsonObject -> {
                        val responseObj = JSONObject(responseString)
                        callback.onResponse(tag,responseObj)
                    }
                    is JsonArray -> {
                        val responseArray = JSONArray(responseString)
                        callback.onResponse(tag,responseArray)
                    }
                    else -> callback.onErrorResponse(tag,errorCode)
                }
            }catch (e: Exception){
                e.printStackTrace()
                callback.onErrorResponse(tag,errorCode)
            }
        }

        override fun onFailure(call: okhttp3.Call?, e: IOException?) {
            callback.onErrorResponse(tag,errorCode)
        }

    }
}

