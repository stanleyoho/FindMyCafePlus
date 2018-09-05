package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : BasicFragment() {

    private lateinit var myView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = layoutInflater.inflate(R.layout.fragment_setting,container,false)

        initEvent()
        return myView
    }


    private fun initEvent(){
        myView.btnSettingAbout.setOnClickListener(clickListener)
        myView.btnSettingLogout.setOnClickListener(clickListener)
    }

    private val clickListener = object : View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.btnSettingAbout ->{}
                R.id.btnSettingLogout ->{}
            }
        }
    }
}