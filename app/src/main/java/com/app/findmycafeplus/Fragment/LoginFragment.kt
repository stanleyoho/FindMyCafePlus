package com.app.findmycafeplus.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.app.findmycafeplus.Utils.LogUtils
import com.app.findmycafeplus.Utils.Utils
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : BaseFragment() {

    private val auth = AccountLoginManager.getAuthInstance()

    private val facebookCallBackManager = CallbackManager.Factory.create()
    private lateinit var  loginFragmentView : View
    private val GOOGLE_SIGN_IN = 100

    companion object {
        fun newInstance(bundle: Bundle?): Fragment {
            val fragment = LoginFragment()
            if (bundle != null) {
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        loginFragmentView = inflater.inflate(R.layout.fragment_login, container, false)
        initEvent(loginFragmentView)
        signInWithFacebook(loginFragmentView)
        return loginFragmentView
    }

    private fun initEvent(view : View){
        view.btnLoginMail.setOnClickListener(onclickListener)
        view.btnLoginFacebook.setOnClickListener(onclickListener)
        view.btnLoginGoogle.setOnClickListener(onclickListener)
        view.btnLogin.setOnClickListener(onclickListener)
        view.btnLoginFacebook.setOnClickListener(onclickListener)

        //setDisable
        view.btnLogin.isEnabled = false

        //set textChangeListener
        view.editLoginAccount.addTextChangedListener(textChangeListener)
        view.editLoginPwd.addTextChangedListener(textChangeListener)

    }

    private var textChangeListener = object : TextWatcher{
        override fun afterTextChanged(s: Editable?) {
            val accountText = loginFragmentView.editLoginAccount.text.toString().length
            val pwdText = loginFragmentView.editLoginPwd.text.toString().length
            btnLogin.isEnabled = accountText > 0 && pwdText >0
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private var onclickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when (viewId) {
            R.id.btnLoginMail -> fragmentChangeListener.switchFragment(PageName.REGISTER)
            R.id.btnLoginGoogle -> {
                setLoadDialog()
                signInWithGoogle()
            }
            R.id.btnLogin -> {
                setLoadDialog()
                signInWithMail()
            }
            R.id.btnLoginFacebook ->{
                setLoadDialog()
            }
        }
    }

    private fun signInWithMail() {
        val mail = editLoginAccount.text.toString()
        val pws = editLoginPwd.text.toString()
        AccountLoginManager.loginWithMail(mail, pws)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        Utils.showToast(context!!, "main login success")
                        loginSuccess(Constants.LOGIN_TYPE_EMAIL,mail)
                    } else {
                        Utils.showToast(context!!, "main login fail")
                    }
                }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(context!!, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //facebook
        facebookCallBackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            val account = task.signInAccount
            authWithGoogle(account)
        }
    }


    private fun authWithGoogle(account: GoogleSignInAccount?) {
        if (account == null){
            setLoadDialog()
            return
        }
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                loginSuccess(Constants.LOGIN_TYPE_GOOGLE,account.email!!)
                Utils.showToast(context!!, "google login success")
            } else {
                loginFail()
                Utils.showToast(context!!, "google login fail")
            }
            AccountLoginManager.logUserInfo()
        }
    }

    private fun signInWithFacebook(view: View) {
        view.btnLoginFacebook.setReadPermissions("email", "public_profile")

        view.btnLoginFacebook.registerCallback(facebookCallBackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                LogUtils.e("facebook:onCancel:", "onCancel")
                setLoadDialog()
            }

            override fun onError(error: FacebookException?) {
                LogUtils.e("facebook:onError:", error.toString())
                setLoadDialog()
            }

            override fun onSuccess(result: LoginResult?) {
                LogUtils.e("facebook:onSuccess:", result.toString())
                handleFacebookAccessToken(result!!.accessToken)
                AccountLoginManager.logUserInfo()
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val cred = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(cred)
                .addOnCompleteListener(activity!!) { p0 ->
                    if (p0.isSuccessful) {
                        loginSuccess(Constants.LOGIN_TYPE_FACEBOOK,AccountLoginManager.getUserInfo()?.mail!!)
                        LogUtils.e("facebook:onSuccess:", "success")
                    } else {
                        loginFail()
                        LogUtils.e("facebook:fail:", "fail")
                    }
                }
    }

    private fun loginSuccess(loginType : Int , mail : String){
        UserPreference(context!!).loginType = loginType
        UserPreference(context!!).mail= mail
        setLoadDialog()
        fragmentChangeListener.switchFragment(PageName.BACK_TO_MAIN)
    }

    private fun loginFail(){
        setLoadDialog()
    }

    private fun setLoadDialog(){
        fragmentChangeListener.switchFragment(PageName.SHOW_OR_DISMISS_LOADING)
    }

}