package com.app.findmycafeplus.CustomView

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.Utils

class RatingStarView : LinearLayout {

    private var totalStars : Int
    private var currentRatingValue : Float = 0.0f

    constructor(context : Context):this(context,null)

    constructor(context : Context , attrs : AttributeSet?): this(context,attrs,0)

    constructor(context : Context , attrs : AttributeSet?, defStyleAttr : Int ): super(context,attrs,defStyleAttr){

        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ratingStarView,0,0)

        try{
            totalStars = typeArray.getInteger(R.styleable.ratingStarView_starValue,5)

        }finally {
            typeArray.recycle()
        }

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        this.layoutParams = layoutParams
        this.gravity = Gravity.CENTER
        this.orientation = LinearLayout.HORIZONTAL

        initStars(totalStars)
    }

    private fun initStars(totalValue : Int){
        removeAllViews()
        for(index in 0 until totalValue){
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.layoutParams = LinearLayout.LayoutParams(Utils.px2Dp(context,150f),Utils.px2Dp(context,150f))
            setStarDrawable(imageView,StarType.EMPTY)
            addView(imageView)
        }
    }

    fun drawStars(currentValue : Float){
        currentRatingValue = currentValue

        val totalCount = childCount

        val valueInteger : Int = (currentRatingValue/ 1.0).toInt()
        val valueRemainder : Float = currentRatingValue % 1f
        var isSetHalfStar : Boolean = false

        for(index in 0 until totalCount){
            val tempView = getChildAt(index)
            if(tempView is ImageView){
                if(index < valueInteger){
                    setStarDrawable(tempView,StarType.FULL)
                }else{
                    if(valueRemainder != 0.0f && !isSetHalfStar){
                        setStarDrawable(tempView,StarType.HALF)
                        isSetHalfStar = true
                    }else{
                        setStarDrawable(tempView,StarType.EMPTY)
                    }
                }
            }
        }
    }

    private fun setStarDrawable(image : ImageView ,type : StarType){
        var starId = 0
        starId = when(type){
            StarType.EMPTY ->{
                R.drawable.ic_star_empty
            }
            StarType.HALF ->{
                R.drawable.ic_star_half
            }
            StarType.FULL ->{
                R.drawable.ic_star_full
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            image.setImageDrawable(context.getDrawable(starId))
        }else{
            image.setImageDrawable(context.resources.getDrawable(starId))
        }
    }

    enum class StarType{
        FULL,
        HALF,
        EMPTY
    }
}