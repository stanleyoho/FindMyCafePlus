package com.app.findmycafeplus.Activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.view.View
import android.widget.Toast
import com.app.findmycafeplus.CustomView.FilterDialog
import com.app.findmycafeplus.Fragment.MapFragment
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initEvent()
    }

    private fun initView(){

        val transaction = supportFragmentManager
        navMain.inflateMenu(R.menu.menu_nav_main_singin)
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
//                Toast.makeText(this,"Donate",Toast.LENGTH_SHORT).show()
                FilterDialog(this).show()
            }
        }
    }

    private var onNavItemSelectListener = NavigationView.OnNavigationItemSelectedListener { item ->
        val viewId = item.itemId
        when(viewId){
            R.id.menuMap ->{
                Toast.makeText(this,"Map",Toast.LENGTH_SHORT).show()
            }
            R.id.menuMember ->{
                Toast.makeText(this,"Member",Toast.LENGTH_SHORT).show()
            }
            R.id.menuLogin ->{
                Toast.makeText(this,"Login",Toast.LENGTH_SHORT).show()
            }
            R.id.menuSetting ->{
                Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show()
            }
            R.id.menuLogout ->{
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
            }
        }
        setDrawerVisible()
        false
    }
}
