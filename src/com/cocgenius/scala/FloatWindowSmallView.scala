package com.cocgenius.scala

import org.scaloid.common._

class FloatWindowSmallView extends SLinearLayout {
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var layoutParams: WindowManager.LayoutParams = null
    private var xInScreen: Float = 0
    private var yInScreen: Float = 0
    private var xDownInScreen: Float = 0
    private var yDownInScreen: Float = 0
    private var xInView: Float = 0
    private var yInView: Float = 0

    def this(context: SContext) {
        
    }
}