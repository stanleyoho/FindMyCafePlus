package com.app.findmycafeplus.CustomView

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.app.findmycafeplus.Constants.Constants
import com.app.findmycafeplus.Preference.LevelPreference
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.layout_level_view.view.*

class LevelView : LinearLayout{

    private var textLevel : TextView
    private var textXp : TextView
    private var pbValue : ProgressBar

    constructor(context : Context):this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

        val view =  inflate(context, R.layout.layout_level_view,this)

        textLevel = view.textLevelViewLevel
        textXp = view.textLevelViewXp
        pbValue = view.pbLevelViewProgress

        val level = LevelPreference(context).level
        val currentXp = LevelPreference(context).experience
        updateCurrentLevel(context,level,currentXp)
    }

    private fun setXp(context: Context , current : Int , max : Int){
        val temp = context.getString(R.string.level_view_xp)
        textXp.text = String.format(temp,current,max)
        pbValue.max = max
    }

    private fun setLevel(context: Context , level : Int){
        val temp = context.getString(R.string.level_view_level)
        textLevel.text = String.format(temp,level)
    }

    private fun setProgress( current : Int){
        pbValue.progress = current
    }

    fun updateCurrentLevel(context: Context, level : Int, currentXp : Int){
        var currentLevel : Int = level
        var currentXp : Int = currentXp
        if(currentXp >=  Constants.LEVEL_ARRAY[level]){
            currentLevel += 1
            currentXp = 0
        }
        setLevel(context,currentLevel)
        setXp(context,currentXp,Constants.LEVEL_ARRAY[currentLevel])
        setProgress(currentXp)
        LevelPreference(context).level = currentLevel
        LevelPreference(context).experience = currentXp
    }
}