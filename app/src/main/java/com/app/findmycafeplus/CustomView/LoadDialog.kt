package com.app.findmycafeplus.CustomView

import android.app.Dialog
import android.content.Context
import com.app.findmycafeplus.R
import com.app.findmycafeplus.Utils.Utils

class LoadDialog : Dialog {

    constructor(context: Context?) : this(context,0)
    constructor(context: Context?, themeResId: Int) : super(context, R.style.fullDialog){
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        Utils.setFullScreenDialog(this)
    }

}