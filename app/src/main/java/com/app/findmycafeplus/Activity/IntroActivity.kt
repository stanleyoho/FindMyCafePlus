package com.app.findmycafeplus.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.view.View
import com.app.findmycafeplus.Interface.ApiCallBackInterface
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Manager.ApiManager
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.LogUtils
import com.app.findmycafeplus.Utils.PermissionCheckUtils
import com.app.findmycafeplus.Utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_intro.*
import org.json.JSONArray
import org.json.JSONObject

class IntroActivity : BaseActivity() {

    val API_TAIPEI = 1001
    val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()


        //正式
        checkPermission()

        //test
//        goToMain()

    }


    override fun initEvent() {

    }

    override fun initView() {
        setContentView(R.layout.activity_intro)
    }

    private fun checkPermission() {

        val permissions = PermissionCheckUtils.Companion.Builder(this)
                .setCameraPermissionCheck()
                .setReadStoragePermissionCheck()
                .setWriteStoragePermissionCheck()
                .setLocationFindPermissionCheck()
                .setLocationCoarsePermissionCheck()
                .build()

        if (permissions.getPersmissions().isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.getPersmissions(), Constants.PERMISSION_CODE)
        } else {
            callApi()
        }
    }

    private fun callApi() {
        ApiManager.apiGetTaipeiCafeInformation(API_TAIPEI, apiCallBackInterface)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.PERMISSION_CODE){
            if(grantResults.contains(-1)){
                checkPermission()
            }else{
                callApi()
            }
        }
    }

    private var apiCallBackInterface = object : ApiCallBackInterface {
        override fun onResponse(tag: Int, response: JSONObject) {
            when (tag) {
                API_TAIPEI -> {

                }
            }
        }

        override fun onResponse(tag: Int, response: JSONArray) {
            val cafeList = ArrayList<RMCafeInformation>()
            when (tag) {
                API_TAIPEI -> {
                    //set progress max
                    LogUtils.e("onResponse", "Realm size :" + RMCafeInformation().getAll().size)
                    LogUtils.e("onResponse", "response size :" + response.length())
                    if (response.length() != RMCafeInformation().getAll().size) {
                        runOnUiThread {
                            layoutLoading.visibility = View.VISIBLE
                            pgLoading.max = response.length()
                        }

                        RMCafeInformation().deleteAll()
                        for (index in 0 until response.length()) {
                            val cafeInfo = Gson().fromJson(response.get(index).toString(), RMCafeInformation::class.java)
                            var nearestDistance = 0.0
                            for (mrt in DataManager.getMrtList()) {
                                val tempDistance = Utils.GetDistance(cafeInfo.latitude, cafeInfo.longitude, mrt.latitude, mrt.longitude)
                                if (nearestDistance == 0.0) {
                                    nearestDistance = tempDistance
                                }
                                if (tempDistance < nearestDistance) {
                                    nearestDistance = tempDistance

                                    //重新設定最靠近哪條支線的捷運
                                    cafeInfo.restLines()
                                    //設定最接近的站
                                    cafeInfo.nearestStationName = mrt.station_name_chinese
                                    //設定為哪條捷運路線
                                    cafeInfo.setLineType(mrt.station_line_id)
                                }
                            }
                            cafeList.add(cafeInfo)

                            //update progress
                            pgLoading.progress = cafeList.size
                        }
                    }
                    RMCafeInformation().addAll(cafeList)
                    goToMain()
                }
            }
        }

        override fun onErrorResponse(tag: Int, errorMessage: String) {
            when (tag) {
                API_TAIPEI -> {
                    goToMain()
                }
            }
        }
    }

    private fun goToMain() {
        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 500)
    }
}