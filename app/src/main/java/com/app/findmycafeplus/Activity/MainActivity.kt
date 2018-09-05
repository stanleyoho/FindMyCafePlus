package com.app.findmycafeplus.Activity

import android.content.*
import android.os.Bundle
import android.preference.Preference
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.view.View
import android.widget.Toast
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Fragment.MapFragment
import com.app.findmycafeplus.Fragment.NewsFragment
import com.app.findmycafeplus.Fragment.SettingFragment
import com.app.findmycafeplus.Manager.AccountLoginManager
import com.app.findmycafeplus.Preference.FilterPreference
import com.app.findmycafeplus.Preference.LevelPreference
import com.app.findmycafeplus.Preference.UserPreference
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_nav_header.view.*

class MainActivity : BasicActivity() {

    private lateinit var navHeaderView: View
    private lateinit var levelPreference: LevelPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelPreference = LevelPreference(this)

        initView()
        initNavigation()
        initEvent()
    }

    override fun onResume() {
        super.onResume()
        loginCheck()
        levelPreference.addLevelChangeListener(levelPreferenceChangeListener)
        LocalBroadcastManager.getInstance(this).registerReceiver(levelBroadcastReceiver, IntentFilter(Constants.LEVEL_BROADCAST_INTENT))
    }

    override fun onPause() {
        super.onPause()
        levelPreference.removeLevelChangeListener(levelPreferenceChangeListener)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(levelBroadcastReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        FilterPreference(this).reset()
    }

    /**
     * init view
     * */
    override fun initView() {
        addFragment(R.id.fragmentMain, MapFragment(), "")
    }

    /**
     * init event
     * */
    override fun initEvent() {
        btnMenu.setOnClickListener(onclickListener)
        btnDonate.setOnClickListener(onclickListener)
        navMain.setNavigationItemSelectedListener(onNavItemSelectListener)
    }

    /**
     * init navigation view
     * */
    private fun initNavigation() {
        navHeaderView = navMain.getHeaderView(0)
    }

    private fun loginCheck() {
        navMain.menu.clear()
        if (AccountLoginManager.isLogin()) {
            navMain.inflateMenu(R.menu.menu_nav_main_singin)

            navHeaderView.lvNav.visibility = View.VISIBLE
            navHeaderView.ivNavLoginType.visibility = View.VISIBLE
            when (UserPreference(this).loginType) {
                Constants.LOGIN_TYPE_EMAIL -> {
                    navHeaderView.ivNavLoginType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_login_mail))
                }
                Constants.LOGIN_TYPE_FACEBOOK -> {
                    navHeaderView.ivNavLoginType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.com_facebook_button_icon_blue))
                }
                Constants.LOGIN_TYPE_GOOGLE -> {
                    navHeaderView.ivNavLoginType.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_google_login))
                }
            }
            val userInfo = AccountLoginManager.getUserInfo()
            navHeaderView.textNavMemberMail.text = userInfo?.mail
        } else {
            navMain.inflateMenu(R.menu.menu_nav_main_unsing)
            navHeaderView.lvNav.visibility = View.GONE
            navHeaderView.ivNavLoginType.visibility = View.GONE
            navHeaderView.textNavMemberMail.text = "未登入"
        }
    }

    /**
     * 開啟/關閉 navigationBar
     * */
    private fun setDrawerVisible() {
        if (drawMain.isDrawerOpen(GravityCompat.START)) {
            drawMain.closeDrawer(GravityCompat.START)
        } else {
            drawMain.openDrawer(GravityCompat.START)
        }
    }

    /**
     * onClick listener
     * */
    private var onclickListener = View.OnClickListener { v ->
        val viewId = v?.id
        when (viewId) {
            R.id.btnMenu -> {
                setDrawerVisible()
            }
            R.id.btnDonate -> {
                Toast.makeText(this, "Donate", Toast.LENGTH_SHORT).show()
                levelPreference.experience++
            }
        }
    }

    /**
     * navigation select listener
     * */
    private var onNavItemSelectListener = NavigationView.OnNavigationItemSelectedListener { item ->
        val viewId = item.itemId
        when (viewId) {
            R.id.menuNewsSigned, R.id.menuNewsUnSign -> {
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                replaceAndAddToBackStack(R.id.fragmentMain,NewsFragment(),"")
            }
            R.id.menuMapSigned, R.id.menuMapUnSign -> {
                Toast.makeText(this, "Map", Toast.LENGTH_SHORT).show()
                popBackStackImmediate()
            }
            R.id.menuLogin -> {
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
            R.id.menuSettingSigned, R.id.menuSettingUnSign -> {
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
                replaceAndAddToBackStack(R.id.fragmentMain,SettingFragment(),"")
            }
//            R.id.menuLogout -> {
//                AccountLoginManager.logout(this@MainActivity)
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
//                loginCheck()
//            }
        }
        setDrawerVisible()
        false
    }

    /**
     * update level listener
     * */
    private fun updateLevel() {
        navHeaderView.lvNav.updateCurrentLevel(this, levelPreference.level, levelPreference.experience)
    }

    /**
     * level preference change listener
     * */
    private var levelPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        if (key != null && key == Constants.LEVEL_PREFERENCE_XP) {
            var level = levelPreference.level
            var xp = levelPreference.experience

            if (xp >= Constants.LEVEL_ARRAY[level]) {
                level++
                xp = 0
            }
        }
        updateLevel()
    }

    private var levelBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateLevel()
        }
    }

}
