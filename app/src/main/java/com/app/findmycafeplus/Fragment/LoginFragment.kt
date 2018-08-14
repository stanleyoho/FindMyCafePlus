package com.app.findmycafeplus.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.findmycafeplus.Constants.PageName
import com.app.findmycafeplus.Manager.AccountLoginManager
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.LogUtils
import com.app.findmycafeplus.Utils.Utils
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : BasicFragment() {

    private val auth = FirebaseAuth.getInstance()

    private val facebookCallBackManager = CallbackManager.Factory.create()
    private val facebookLoginManager = LoginManager.getInstance()
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

        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.btnLoginMail.setOnClickListener(onclickListener)
        view.btnLoginFacebook.setOnClickListener(onclickListener)
        view.btnLoginGoogle.setOnClickListener(onclickListener)
        view.btnLogin.setOnClickListener(onclickListener)
        //test
        view.btnLoginIsLogin.setOnClickListener(onclickListener)
        view.btnLogout.setOnClickListener(onclickListener)
        signInWithFacebook(view)
        return view
    }

    private var onclickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when (viewId) {
            R.id.btnLoginMail -> fragmentChangeListener.switchFragment(PageName.REGISTER)
            R.id.btnLoginGoogle -> {
                signInWithGoogle()
            }
            R.id.btnLogin -> {
            }

            //text
            R.id.btnLoginIsLogin ->{
                AccountLoginManager.isLogin()
            }
            R.id.btnLogout ->{
                AccountLoginManager.logout()
            }
        }
    }

    private fun signInWithMail() {
        val mail = "stanly7768@hotmail.com"
        val pws = "hau89513717"
        auth.createUserWithEmailAndPassword(mail, pws)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        Utils.showToast(context!!, "main login success")
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
        val signInIntent = mGoogleSignInClient.getSignInIntent()
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
        if (account == null) return
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                Utils.showToast(context!!, "google login success")
            } else {
                Utils.showToast(context!!, "google login fail")
            }
            AccountLoginManager.logUserInfo()
        }
    }

    private fun signInWithFacebook(view : View) {
        view.btnLoginFacebook.setReadPermissions("email", "public_profile")

        view.btnLoginFacebook.registerCallback(facebookCallBackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                LogUtils.e("facebook:onCancel:", "onCancel")
            }

            override fun onError(error: FacebookException?) {
                LogUtils.e("facebook:onError:", error.toString())
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
                        LogUtils.e("facebook:onSuccess:", "success")
                    } else {
                        LogUtils.e("facebook:fail:", "fail")
                    }
                }
    }
}