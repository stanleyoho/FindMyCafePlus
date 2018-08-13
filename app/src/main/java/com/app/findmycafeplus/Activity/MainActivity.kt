package com.app.findmycafeplus.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.View
import android.widget.Toast
import com.app.findmycafeplus.Fragment.MapFragment
import com.app.findmycafeplus.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BasicActivity() {

    val RC_SIGN_IN = 200
    val authInstance : FirebaseAuth? = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initEvent()
    }

    private fun initView(){

        val transaction = supportFragmentManager
        navMain.inflateMenu(R.menu.menu_nav_main_unsing)
        transaction.beginTransaction().add(R.id.fragmentMain,MapFragment(),"").commit()
    }

    private fun initEvent(){
        btnMenu.setOnClickListener(onclickListener)
        btnDonate.setOnClickListener(onclickListener)
        navMain.setNavigationItemSelectedListener(onNavItemSelectListener)
    }

    private fun setDrawerVisible(){
        if(drawMain.isDrawerOpen(GravityCompat.START)){
            drawMain.closeDrawer(GravityCompat.START)
        }else{
            drawMain.openDrawer(GravityCompat.START)
        }
    }

    private var onclickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when(viewId){
            R.id.btnMenu ->{
                setDrawerVisible()
            }
            R.id.btnDonate ->{
                val user = authInstance?.currentUser

                Toast.makeText(this,"Donate",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var onNavItemSelectListener = NavigationView.OnNavigationItemSelectedListener { item ->
        val viewId = item.itemId
        when(viewId){
            R.id.menuMapSigned ,R.id.menuMapUnSign ->{
                Toast.makeText(this,"Map",Toast.LENGTH_SHORT).show()
            }
            R.id.menuMember ->{
                Toast.makeText(this,"Member",Toast.LENGTH_SHORT).show()
            }
            R.id.menuLogin ->{
                Toast.makeText(this,"Login",Toast.LENGTH_SHORT).show()
                startActivity( Intent(this@MainActivity,LoginActivity::class.java))
            }
            R.id.menuSettingSigned ,R.id.menuSettingUnSign ->{
                Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show()
            }
            R.id.menuLogout ->{
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
            }
        }
        setDrawerVisible()
        false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){

            if(resultCode == Activity.RESULT_OK){

            }else{

            }
        }
    }
}
