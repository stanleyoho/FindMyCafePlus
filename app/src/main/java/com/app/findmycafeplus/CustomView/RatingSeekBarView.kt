package com.app.findmycafeplus.CustomView

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.SeekBar
import com.app.findmycafeplus.R
import kotlinx.android.synthetic.main.layout_rating_seekbar.view.*

class RatingSeekBarView : LinearLayout{

    private var resultValue : Float = 0f

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

        inflate(context, R.layout.layout_rating_seekbar,this)

        val typeArray = context.theme.obtainStyledAttributes(attrs,R.styleable.ratingSeekBarView,defStyleAttr,0)

        val ratingTitle = typeArray.getString(R.styleable.ratingSeekBarView_ratingTitle) + " : "

        textRatingTitle.text = ratingTitle

        textRatingValue.text = "0.0"

        sbRating.max = 10

        sbRating.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(seekBar != null){
                    resultValue = progress.toFloat() / 2f
                    textRatingValue.text = resultValue.toString()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        typeArray.recycle()
    }

    fun getRatingValue():Float{
        return resultValue
    }
}