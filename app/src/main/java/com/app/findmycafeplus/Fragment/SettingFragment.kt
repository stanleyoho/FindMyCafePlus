package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFragment : BaseFragment() {

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
                R.id.btnSettingAbout ->{fragmentChangeListener.switchFragment(PageName.OPEN_WEB)}
                R.id.btnSettingLogout ->{fragmentChangeListener.switchFragment(PageName.LOG_OUT)}
            }
        }
    }
}