package com.app.findmycafeplus.CustomView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.app.findmycafeplus.R

class RatingStarView : LinearLayout {

    private var ratingValue : Int = 0

    constructor(context : Context):this(context,null)

    constructor(context : Context , attrs : AttributeSet?): this(context,attrs,0)

    constructor(context : Context , attrs : AttributeSet?, defStyleAttr : Int ): super(context,attrs,defStyleAttr){

        val typeArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ratingStarView,0,0)

        try{
             ratingValue = typeArray.getInteger(R.styleable.ratingStarView_value,5)


        }finally {
            typeArray.recycle()
        }

        for (index in 0 until ratingValue){

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}