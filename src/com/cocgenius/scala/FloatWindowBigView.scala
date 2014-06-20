package com.cocgenius.scala

import org.scaloid.common._

import android.view.LayoutInflater
import android.widget.Button

class FloatWindowBigView extends SLinearLayout {
  var viewWidth: Int = 0
  var viewHeight: Int = 0

  implicit private var mContext: SContext = null

  def this(context: SContext) {
    this.super(context)
    mContext = context
    LayoutInflater.inflate(R.layout.float_window_big, this)
    val view = find[View](R.id.layout_big_view)
    viewWidth = view.getLayoutParams().width
    viewHeight = view.getLayoutParams().height
    find[Button](R.id.close).onClick({
      WindowManager.removeBigView()
      WindowManager.removeSmallView()
      stopService[FloatWindowService]
    })
    find[Button](R.id.back).onClick({
      WindowManager.removeBigView()
      WindowManager.createSmallView()
    })
    find[Button](R.id.screenshot).onClick({
      WindowManager.removeBigView()
      ScreenCapture.screenshot()
      WindowManager.createSmallView()
    })
  }
}
