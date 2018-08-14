package com.app.findmycafeplus.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Manager.AccountLoginManager
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : BasicFragment(){

    private lateinit var myView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_register,container,false)
        myView!!.btnRegMail.setOnClickListener(clickListener)
        return myView
    }

    private val clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when(viewId){
            R.id.btnRegMail -> {
                AccountLoginManager.signInWithMail(context!!,myView.editRegAccount.text.toString(),myView.editRegPwd.text.toString())
                AccountLoginManager.isLogin()
                AccountLoginManager.logUserInfo()
            }
        }
    }
}