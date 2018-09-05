package com.app.findmycafeplus.Activity

import android.content.Intent
import android.os.Bundle
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.CustomView.LoadDialog
import com.app.findmycafeplus.Fragment.LoginFragment
import com.app.findmycafeplus.Fragment.RegisterFragment
import com.app.findmycafeplus.Interface.FragmentChangeListener
import com.app.findmycafeplus.R


class LoginActivity : BasicActivity() , FragmentChangeListener{




    private lateinit var loadDialog : LoadDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }

    override fun initView() {
        setContentView(R.layout.activity_login)
        loadDialog = LoadDialog(this)
        addFragment(R.id.loginFragment, LoginFragment.newInstance(null),PageName.LOGIN.toString())
    }

    override fun initEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun switchFragment(pageName: PageName) {
        when(pageName){
            PageName.LOGIN ->{}
            PageName.REGISTER ->{
                replaceAndAddToBackStack(R.id.loginFragment,RegisterFragment(),PageName.LOGIN.toString())
            }
            PageName.BACK_TO_MAIN ->{
                finish()
            }
            PageName.SHOW_OR_DISMISS_LOADING ->{
                if(loadDialog.isShowing){
                    loadDialog.dismiss()
                }else{
                    loadDialog.show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentByTag(PageName.LOGIN.toString())
        fragment.onActivityResult(requestCode,resultCode,data)
    }
}