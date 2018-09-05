package com.app.findmycafeplus.Activity

import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.R.attr.data
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.app.findmycafeplus.Fragment.MapFragment
import com.app.findmycafeplus.Utils.LogUtils
import kotlin.math.log


abstract class BasicActivity : AppCompatActivity() {

    private val fragmentManager : FragmentManager = supportFragmentManager

    abstract fun initView()

    abstract fun initEvent()

    /**
     * 新增第一個Fragment
     * @param layout      畫面id
     * @param fragment  Fragment實體物件
     * */
    fun addFragment(@IdRes layout : Int, fragment : Fragment, tag : String){
        val transaction = fragmentManager.beginTransaction()
        transaction.add(layout,fragment,tag)
        transaction.commit()
    }

    fun repalceAndCleanFragment(@IdRes layout : Int , fragment : Fragment , tag : String){
        cleanBackStacks()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(layout,fragment,tag)
        transaction.commit()
    }

    fun replaceAndAddToBackStack(@IdRes layout : Int , fragment : Fragment , tag : String){
        val transaction = fragmentManager.beginTransaction()
        if(fragment !is MapFragment){
            popBackStackImmediate()
        }
        transaction.replace(layout,fragment,tag)
        transaction.addToBackStack(tag)
        transaction.commit()
        Log.e("test","test")
    }

    /**
     * cleanBackStack
     * 清除FragmentManager中的所有堆疊
     * */
    fun cleanBackStacks(){
        var backStackCount = fragmentManager.backStackEntryCount
        while(backStackCount > 0){
            popBackStackImmediate()
            backStackCount--
        }
    }

    /**
     * popBackStackImmediate
     * 如果有堆疊的Fragment，清除一個堆疊
     * */
    fun popBackStackImmediate(){
        try{
            fragmentManager.popBackStackImmediate()
        }catch ( e : Exception){
            e.printStackTrace()
        }
    }
}