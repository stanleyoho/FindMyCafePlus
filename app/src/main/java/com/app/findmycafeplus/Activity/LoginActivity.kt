package com.app.findmycafeplus.Activity

import android.content.Intent
import android.os.Bundle
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.Fragment.LoginFragment
import com.app.findmycafeplus.Fragment.RegisterFragment
import com.app.findmycafeplus.Interface.FragmentChangeListener
import com.app.findmycafeplus.R


class LoginActivity : BasicActivity() , FragmentChangeListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        addFragmentFirst(R.id.loginFragment, LoginFragment.newInstance(null),PageName.LOGIN.toString())

    }

    override fun switchFragment(pageName: PageName) {

        when(pageName){
            PageName.LOGIN ->{}
            PageName.REGISTER ->{
                replaceAndAddToBackStack(R.id.loginFragment,RegisterFragment(),PageName.LOGIN.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentByTag(PageName.LOGIN.toString())
        fragment.onActivityResult(requestCode,resultCode,data)
    }
}