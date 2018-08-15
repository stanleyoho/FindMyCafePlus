package com.app.findmycafeplus.Manager

import android.content.Context
import com.app.findmycafeplus.Model.CurrentUserInfo
import com.app.findmycafeplus.Utils.LogUtils
import com.app.findmycafeplus.Utils.Utils
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AccountLoginManager {

    //fireBase auth
    private val authInstance : FirebaseAuth = FirebaseAuth.getInstance()

    //facebook manager
    private val facebookManager = LoginManager.getInstance()

    /**
     * get fireBase auth instance
     * */
    fun getAuthInstance() : FirebaseAuth{
        return authInstance
    }

    /**
     * check is login
     * */
    fun isLogin() : Boolean{
        if(isFacebookLogin()){
            LogUtils.d("log current state" , "facebook login" )
        }else if(isGoogleLogin()){
            LogUtils.d("log current state" , "google login" )
        }else{
            LogUtils.d("log current state" , "all logout" )
        }
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
        if(isGoogleLogin() && isFacebookLogin()){
            googleLogout()
            facebookLogout()
        }else if(isFacebookLogin()){
            facebookLogout()
        }else if(isGoogleLogin()){
            googleLogout()
        }else{
            LogUtils.d("log current state" , "all logout" )
        }
    }

    private fun googleLogout(){
        authInstance.signOut()
        LogUtils.d("log current state" , "google logout" )
    }

    private fun facebookLogout(){
        facebookManager.logOut()
        LogUtils.d("log current state" , "facebook logout" )
    }

    /**
     * log current state
     * */
    fun logUserInfo(){
        var userInfo : FirebaseUser? = null
        var profile: Profile ?  =  null
        if (isGoogleLogin()){
            userInfo = authInstance.currentUser
        }else if (isFacebookLogin()){
            profile = Profile.getCurrentProfile()
        }

        LogUtils.d("log current state","***************************************")
        if(userInfo != null){
            LogUtils.d("log current state","*************Google login****************")
            LogUtils.d("log current state" , "uid : " + userInfo.uid)
            LogUtils.d("log current state" , "name : " + userInfo.displayName)
            LogUtils.d("log current state" , "email : " + userInfo.email)
            LogUtils.d("log current state" , "photoUrl : " + userInfo.photoUrl)
            LogUtils.d("log current state" , "phoneNumber : " + userInfo.phoneNumber)
            LogUtils.d("log current state" , "isEmailVerified : " + userInfo.isEmailVerified)
        }else if(profile != null){
            LogUtils.d("log current state","*************Facebook login****************")
            LogUtils.d("log current state" , "id : " + profile.id)
            LogUtils.d("log current state" , "firstName : " + profile.firstName)
            LogUtils.d("log current state" , "middleName : " + profile.middleName)
            LogUtils.d("log current state" , "lastName : " + profile.lastName)
            LogUtils.d("log current state" , "linkUri : " + profile.linkUri)
            LogUtils.d("log current state" , "name : " + profile.name)
            LogUtils.d("log current state" , "getProfilePictureUri : " + profile.getProfilePictureUri(80,80))
        }else if(userInfo == null && profile == null){
            LogUtils.d("log current state","*************all login out****************")
        }
        LogUtils.d("log current state","***************************************")

    }

    fun signInWithMail(mail : String , pwd : String) : Task<AuthResult> {
        return authInstance.createUserWithEmailAndPassword(mail,pwd)
    }

    fun loginWithMail(mail : String , pwd : String) : Task<AuthResult>{
        return authInstance.signInWithEmailAndPassword(mail,pwd)
    }

    fun getUserInfo() : CurrentUserInfo? {
        var userInfo : FirebaseUser? = null
        var profile: Profile ?  =  null
        var currentUserInfo : CurrentUserInfo? = null
        if (isGoogleLogin()){
            userInfo = authInstance.currentUser
            currentUserInfo = CurrentUserInfo(userInfo?.email,userInfo?.photoUrl.toString())
        }else if (isFacebookLogin()){
            profile = Profile.getCurrentProfile()
            currentUserInfo = CurrentUserInfo(profile.name,profile.getProfilePictureUri(80,80).toString())
        }

        return currentUserInfo
    }
}