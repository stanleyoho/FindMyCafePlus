package com.app.findmycafeplus.Manager

import android.content.Context
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Model.MrtInformation
import com.app.findmycafeplus.Utils.LogUtils
import com.google.gson.Gson
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import kotlin.math.log

object DataManager {

    private var mrtDataList : ArrayList<MrtInformation> = ArrayList()
    private var  redLineArrayList = ArrayList<String>()
    private var  blueLineArrayList = ArrayList<String>()
    private var  brownLineArrayList = ArrayList<String>()
    private var  orangeLineArrayList = ArrayList<String>()
    private var  greenLineArrayList = ArrayList<String>()

    fun getMrtList(): ArrayList<MrtInformation> {
        return mrtDataList
    }

    fun initData(context: Context) {

        LogUtils.e("init","init start")
        try {
            val inputStream: InputStream = context.assets.open("mrt_final.json")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val mrtDataString = String(buffer, Charsets.UTF_8)

            val mrtDataArray = JSONArray(mrtDataString)

            for (index in 0 until mrtDataArray.length()) {
                val mrtInformation = Gson().fromJson(mrtDataArray.get(index).toString(), MrtInformation::class.java)
                mrtDataList.add(mrtInformation)
                when(mrtInformation.station_line_id){
                    "BR" ->{
                        brownLineArrayList.add(mrtInformation.station_name_chinese)
                    }
                    "BL" ->{
                        blueLineArrayList.add(mrtInformation.station_name_chinese)
                    }
                    "R" ->{
                        redLineArrayList.add(mrtInformation.station_name_chinese)
                    }
                    "G" ->{
                        greenLineArrayList.add(mrtInformation.station_name_chinese)
                    }
                    "O" ->{
                        orangeLineArrayList.add(mrtInformation.station_name_chinese)
                    }
                }
            }

            brownLineArrayList.add(0,"不拘")
            blueLineArrayList.add(0,"不拘")
            redLineArrayList.add(0,"不拘")
            greenLineArrayList.add(0,"不拘")
            orangeLineArrayList.add(0,"不拘")
            LogUtils.e("DataManager", "initData success size : " + mrtDataList.size)
        } catch (e: IOException) {
            e.printStackTrace()
            LogUtils.e("DataManager", "initData Fail ")
        }
        LogUtils.e("init","init end")
    }


    fun getStationArray(line : Int) : Array<String>{
        return when(line){
            Constants.LINE_BLUE -> blueLineArrayList.toTypedArray()
            Constants.LINE_RED -> redLineArrayList.toTypedArray()
            Constants.LINE_ORANGE -> orangeLineArrayList.toTypedArray()
            Constants.LINE_BROWN -> brownLineArrayList.toTypedArray()
            Constants.LINE_GREEN -> greenLineArrayList.toTypedArray()
            else -> emptyArray()
        }
    }


}
