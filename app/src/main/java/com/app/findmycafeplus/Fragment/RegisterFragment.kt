package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : BasicFragment() {

    private lateinit var regFragmentView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        regFragmentView = inflater.inflate(R.layout.fragment_register, container, false)
        initEvent(regFragmentView)
        return regFragmentView
    }


    private fun initEvent(myView : View){
        regFragmentView.btnRegMail.setOnClickListener(clickListener)

        regFragmentView.btnRegMail.isEnabled = false
        //add textChangeListener
        regFragmentView.editRegAccount.addTextChangedListener(textChangeListener)
        regFragmentView.editRegPwd.addTextChangedListener(textChangeListener)
        regFragmentView.editRegPwdCheck.addTextChangedListener(textChangeListener)
    }

    private val clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when (viewId) {
            R.id.btnRegMail -> {
                AccountLoginManager.signInWithMail(regFragmentView.editRegAccount.text.toString(), regFragmentView.editRegPwd.text.toString())
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

    private var textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val accountText = regFragmentView.editRegAccount.text.toString().length
            val pwdText = regFragmentView.editRegPwd.text.toString()
            val pwdCheckText = regFragmentView.editRegPwdCheck.text.toString()

            regFragmentView.btnRegMail.isEnabled = accountText > 0 && pwdText.isNotEmpty() && pwdCheckText.isNotEmpty() && pwdText == pwdCheckText
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun loginSuccess(loginType: Int) {
        UserPreference(context!!).loginType = loginType
        fragmentChangeListener.switchFragment(PageName.BACK_TO_MAIN)
    }
}