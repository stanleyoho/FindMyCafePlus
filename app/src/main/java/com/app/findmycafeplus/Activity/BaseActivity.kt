package com.app.findmycafeplus.Activity

import android.content.Intent
import android.net.Uri
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.Utils.LogUtils
import com.app.findmycafeplus.Utils.PermissionCheckUtils


abstract class BaseActivity : AppCompatActivity() {

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

    fun replaceAndAddToBackStack(@IdRes layout: Int, mFragment: Fragment, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val backStacksCont = fragmentManager.backStackEntryCount
        val currentFragment = fragmentManager.fragments[0].tag
        LogUtils.e("replaceAndAddToBackStack", "backStacksCont = " + backStacksCont.toString())
        LogUtils.e("replaceAndAddToBackStack", "currentFragment = " + currentFragment)
        LogUtils.e("replaceAndAddToBackStack", "replaceFragment = " + tag)

        if (tag != currentFragment) {
            if (backStacksCont > 0) {
                if(tag == PageName.MAP.toString()){
                    popBackStackImmediate()
                }else{
                    transaction.replace(layout, mFragment, tag)
                    transaction.commit()
                }
            }else{
                transaction.replace(layout, mFragment, tag)
                transaction.addToBackStack(tag)
                transaction.commit()
            }
        }
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

    fun initPerminnsion(){
        val permissions = PermissionCheckUtils.Companion.Builder(this)
                .setCameraPermissionCheck()
                .setReadStoragePermissionCheck()
                .setWriteStoragePermissionCheck()
                .setLocationFindPermissionCheck()
                .setLocationCoarsePermissionCheck()
                .build()

        if (permissions.getPersmissions().isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.getPersmissions(), Constants.PERMISSION_CODE)
        }
    }

    fun showLoading(){

    }

    fun dismissLoading(){

    }

    fun openWeb( url : String){
        try{
            val uri: Uri = Uri.parse(url)
            val intent : Intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }catch (e : Exception){
            e.printStackTrace()
        }

    }
}