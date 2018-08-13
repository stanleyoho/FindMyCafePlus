package com.app.findmycafeplus.Manager

import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Utils.LogUtils
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AccountLoginManager {

    //fireBase auth
    private val authInstance : FirebaseAuth = FirebaseAuth.getInstance()

    //facebook manager
    private val facebookManager = LoginManager.getInstance()


    /**
     * check is login
     * */
    fun isLogin() : Boolean{
        return !(!isFacebookLogin() && !isGoogleLogin())
    }

    private fun isFacebookLogin() : Boolean{
        val facebookToken = AccessToken.getCurrentAccessToken()
        return !(facebookToken == null || facebookToken.isExpired)
    }

    private fun isGoogleLogin() : Boolean{
        val user = authInstance.currentUser
        return user != null
    }



    /**
     * logout
     * */
    fun logout() {
        if(isGoogleLogin()){
            googleLogout()
        }else if(isFacebookLogin()){
            facebookLogout()
        }
    }

    private fun googleLogout(){
        authInstance.signOut()
    }

    private fun facebookLogout(){
        facebookManager.logOut()
    }

    /**
     * get user info
     * */
    fun getUser(){
        var userInfo : FirebaseUser? = null
        var profile = Profile.getCurrentProfile()
        if (isGoogleLogin()){
            userInfo = authInstance.currentUser
        }else if (isFacebookLogin()){

        }

//        LogUtils.d("isLogin","***************************************")
//        LogUtils.d("isLogin" , "uid : " + user?.uid)
//        LogUtils.d("isLogin" , "name : " + user?.displayName)
//        LogUtils.d("isLogin" , "email : " + user?.email)
//        LogUtils.d("isLogin" , "photoUrl : " + user?.photoUrl)
//        LogUtils.d("isLogin" , "phoneNumber : " + user?.phoneNumber)
//        LogUtils.d("isLogin" , "isEmailVerified : " + user?.isEmailVerified)
//        LogUtils.d("isLogin","***************************************")
    }

}