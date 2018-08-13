package com.app.findmycafeplus.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.app.findmycafeplus.Manager.AccountLoginManager
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.LogUtils
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : BasicActivity(){

    private val GOOGLE_SIGN_IN = 100

    private val auth = FirebaseAuth.getInstance()

    private val facebookCallBackManager = CallbackManager.Factory.create()
    private val facebookLoginManager = LoginManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        addFragmentFirst(R.id.loginFragment,LoginFragment(),"")
        btng.setOnClickListener(clickListener)
        btnm.setOnClickListener(clickListener)
        btnIsLogin.setOnClickListener(clickListener)
        btnLogout.setOnClickListener(clickListener)
        btnGet.setOnClickListener(clickListener)
        signInWithFacebook()

    }

    private var clickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when(viewId){
            R.id.btng ->{
                signInWithGoogle()
            }
            R.id.btnm ->{
                signInWithMail()
            }
            R.id.btnIsLogin ->{
                if(AccountLoginManager.isLogin()){
                    Toast.makeText(this@LoginActivity,"status : login",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@LoginActivity,"status : login out",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnLogout ->{
                AccountLoginManager.logout()
            }
            R.id.btnGet ->{
                val user = FirebaseAuth.getInstance().currentUser
                val profile = Profile.getCurrentProfile()
                LogUtils.e("test","test")
            }
        }
    }

    private fun signInWithMail(){
        val mail = "stanly7768@hotmail.com"
        val pws = "hau89513717"
        auth.createUserWithEmailAndPassword(mail,pws)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful){
                        Toast.makeText(this@LoginActivity,"main login success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@LoginActivity,"main login fail",Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun signInWithGoogle(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //facebook
        facebookCallBackManager.onActivityResult(requestCode,resultCode,data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task =  Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                val account = task.signInAccount
                authWithGoogle(account)
            }
    }


    private fun authWithGoogle(account : GoogleSignInAccount?){
        if(account == null) return
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                Toast.makeText(this@LoginActivity,"google login success",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity,"google login fail",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithFacebook(){
        btnf.setReadPermissions("email","public_profile")

        btnf.registerCallback( facebookCallBackManager , object : FacebookCallback<LoginResult>{
            override fun onCancel() {
                LogUtils.e("facebook:onCancel:","onCancel")
            }

            override fun onError(error: FacebookException?) {
                LogUtils.e("facebook:onError:",error.toString())
            }

            override fun onSuccess(result: LoginResult?) {
                LogUtils.e("facebook:onSuccess:",result.toString())
                handleFacebookAccessToken(result!!.accessToken)
            }
        })
    }

    private fun handleFacebookAccessToken(token : AccessToken){
        val cred = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(cred)
                .addOnCompleteListener(this@LoginActivity) { p0 ->
                    if(p0.isSuccessful){
                        LogUtils.e("facebook:onSuccess:","success")
                    }else{
                        LogUtils.e("facebook:fail:","fail")
                    }
                }
    }
}