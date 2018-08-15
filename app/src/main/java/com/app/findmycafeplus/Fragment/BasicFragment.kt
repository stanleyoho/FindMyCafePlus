package com.app.findmycafeplus.Fragment

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.app.findmycafeplus.Interface.FragmentChangeListener

open class BasicFragment : Fragment() {

    lateinit var fragmentChangeListener : FragmentChangeListener


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if(context is FragmentChangeListener){
            try {
                fragmentChangeListener = context as FragmentChangeListener
            } catch (e: ClassCastException) {
                throw ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener")
            }
        }

    }
}