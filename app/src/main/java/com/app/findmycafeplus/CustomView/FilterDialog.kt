package com.app.findmycafeplus.CustomView

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Constants.Constants.LINE_BLUE
import com.app.findmycafeplus.Constants.Constants.LINE_BROWN
import com.app.findmycafeplus.Constants.Constants.LINE_GREEN
import com.app.findmycafeplus.Constants.Constants.LINE_ORANGE
import com.app.findmycafeplus.Constants.Constants.LINE_RED
import com.app.findmycafeplus.Interface.FilterDialogCallBackInterface
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.Manager.RealManager
import com.app.findmycafeplus.Model.RMCafeInformation
import com.app.findmycafeplus.Preference.FilterPreference
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.Utils
import io.realm.RealmResults
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog : Dialog{

    private var callBack : FilterDialogCallBackInterface? = null
    private var preference : FilterPreference? = null


    constructor(context: Context) : this(context,0)

    constructor(context: Context , callBack : FilterDialogCallBackInterface):this(context){
        this.callBack = callBack
    }

    constructor(context: Context, themeResId: Int) : super(context, R.style.fullDialog){

        setContentView(R.layout.dialog_filter)

        preference = FilterPreference(context)

        initView()

        initEvent()

        setCancelable(false)

        Utils.setFullScreenDialog(this)

        initDialogItem()
    }

    private fun initView(){
        spLine.adapter = ArrayAdapter.createFromResource(context, R.array.line_strings, android.R.layout.simple_spinner_dropdown_item)
    }

    private fun initEvent(){
        btnCancel.setOnClickListener(clickListener)
        btnConfirm.setOnClickListener(clickListener)
        spLine.onItemSelectedListener = itemSelectedListener
    }


    private var clickListener = View.OnClickListener {
        val viewId = it.id
        when(viewId){
            R.id.btnConfirm -> {
                if(callBack != null || preference != null){
                    preference?.setWifi(ratingWifi.getRatingValue())
                    preference?.setSeat(ratingSeat.getRatingValue())
                    preference?.setQuite(ratingQuite.getRatingValue())
                    preference?.setCheap(ratingCheap.getRatingValue())
                    preference?.setTasty(ratingTasty.getRatingValue())

                    callBack?.filterResult(getFilterResult())

                    if(spLine.selectedItemPosition != 0){
                        preference?.setLine(spLine.selectedItemPosition)
                        preference?.setStation(spStation.selectedItemPosition)
                    }

                    dismiss()
                }
            }

            R.id.btnCancel -> dismiss()
        }
    }

    private var itemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if(parent != null && view != null){
                when(position){
                    0 -> {
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, emptyArray<String>())
                    }
                    Constants.LINE_RED -> {
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, DataManager.getStationArray(Constants.LINE_RED))
                    }
                    Constants.LINE_BROWN ->{
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, DataManager.getStationArray(Constants.LINE_BROWN))
                    }
                    Constants.LINE_GREEN ->{
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, DataManager.getStationArray(Constants.LINE_GREEN))
                    }
                    Constants.LINE_ORANGE ->{
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, DataManager.getStationArray(Constants.LINE_ORANGE))
                    }
                    Constants.LINE_BLUE ->{
                        spStation.adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, DataManager.getStationArray(Constants.LINE_BLUE))
                    }
                }
            }
        }
    }

    private fun initDialogItem(){

        spLine.setSelection(preference!!.getLine())

        spStation.post(Runnable { spStation.setSelection(preference!!.getStation()) })

        ratingWifi.setRatingValue(preference!!.getWifi())
        ratingCheap.setRatingValue(preference!!.getCheap())
        ratingSeat.setRatingValue(preference!!.getSeat())
        ratingQuite.setRatingValue(preference!!.getQuite())
        ratingTasty.setRatingValue(preference!!.getTasty())
    }

    private fun getFilterResult() : RealmResults<RMCafeInformation>{
        val query = RealManager.getRealm().where(RMCafeInformation::class.java)
                .greaterThan("wifi",preference!!.getWifi())
                .greaterThan("seat",preference!!.getSeat())
                .greaterThan("quiet",preference!!.getQuite())
                .greaterThan("cheap",preference!!.getCheap())
                .greaterThan("tasty",preference!!.getTasty())

        when(spLine.selectedItemPosition){
            LINE_RED -> { query.equalTo("isRedLine",true) }
            LINE_BROWN -> { query.equalTo("isBrownLine",true) }
            LINE_GREEN ->{  query.equalTo("isGreenLine",true)}
            LINE_ORANGE ->{ query.equalTo("isOrangeLine",true)}
            LINE_BLUE ->{ query.equalTo("isBlueLine",true)}
        }
        if(spStation.selectedItemPosition != 0 && spStation.adapter.count > 0){
            query.equalTo("nearestStationName",spStation.selectedItem as String)
        }

        return query.findAll()
    }
}