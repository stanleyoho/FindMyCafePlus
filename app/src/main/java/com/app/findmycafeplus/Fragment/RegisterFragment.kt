package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.Manager.AccountLoginManager
import com.app.findmycafeplus.Preference.UserPreference
import com.app.findmycafeplus.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : BasicFragment() {

    private lateinit var myView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_register, container, false)
        myView!!.btnRegMail.setOnClickListener(clickListener)
        return myView
    }

    private val clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when (viewId) {
            R.id.btnRegMail -> {
                AccountLoginManager.signInWithMail(myView.editRegAccount.text.toString(), myView.editRegPwd.text.toString())
                        .addOnCompleteListener { p0 ->
                            if (p0.isSuccessful) {
                                loginSuccess(Constants.LOGIN_TYPE_EMAIL)
                            } else {

                            }
                        }

                AccountLoginManager.isLogin()
                AccountLoginManager.logUserInfo()
            }
        }
    }

    private fun loginSuccess(loginType: Int) {
        UserPreference(context!!).loginType = loginType
        fragmentChangeListener.switchFragment(PageName.BACK_TO_MAIN)
    }
}