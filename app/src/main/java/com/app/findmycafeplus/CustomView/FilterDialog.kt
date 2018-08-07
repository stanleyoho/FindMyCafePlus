package com.app.findmycafeplus.CustomView

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Manager.DataManager
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.Utils
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog : Dialog{

    constructor(context: Context?) : this(context,0)
    constructor(context: Context?, themeResId: Int) : super(context, R.style.fullDialog){

        setContentView(R.layout.dialog_filter)

        initView()

        initEvent()
        setCancelable(false)

        Utils.setFullScreenDialog(this)

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
                //todo
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
}