package com.cocgenius.scala

import org.scaloid.common._

import java.lang.reflect.Field

import android.view.{LayoutInflater,MotionEvent,WindowManager}

class FloatWindowSmallView extends SLinearLayout {
  var viewWidth: Int = 0
  var viewHeight: Int = 0

  private var xInScreen: Float = 0
  private var yInScreen: Float = 0
  private var xDownInScreen: Float = 0
  private var yDownInScreen: Float = 0
  private var xInView: Float = 0
  private var yInView: Float = 0

  implicit private var mContext: SContext = null
  private var mParams: WindowManager.LayoutParams = null
  private var mStatusBarHeight: Int = -1

  def this(context: SContext) {
    this.super()
    mContext = context
    val lWindowManager = WindowManager.getWindowManager
    LayoutInflater.from(context).inflate(R.layout.float_window_small, this)
    val view = findViewById(R.id.layout_small_view)
    viewWidth = view.getLayoutParams().width
    viewHeight = view.getLayoutParams().height
  }

  override def onTouchEvent(event: MotionEvent): Boolean = event.getAction() match {
    case MotionEvent.ACTION_DOWN => {
      xInView = event.getX()
      yInView = event.getY()
      xInScreen = event.getRawX()
      yInScreen = event.getRawY() - getStatusBarHeight()
      xDownInScreen = xInScreen
      yDownInScreen = yInScreen
      true
    }
    case MotionEvent.ACTION_MOVE => {
      xInScreen = event.getRawX()
      yInScreen = event.getRawY() - getStatusBarHeight()
      updateViewPosition()
      true
    }
    case MotionEvent.ACTION_UP => {
      val lMoveThreshold = new object {
        val x = 30
        val y = 30
      }
      if (Math.abs(xDownInScreen - xInScreen) < lMoveThreshold.x &&
        Math.abs(yDownInScreen - yInScreen) < lMoveThreshold.y) {
        openBigView()
      }
      true
    }
    case _ => false
  }

  def setParams(params: WindowManager.LayoutParams) {
    mParams = params;
  }

  private def updateViewPosition() {
    mParams.x = xInScreen - xInView
    mParams.y = yInScreen - yInView
    WindowManager.getWindowManager.updateViewLayout(mParams)
  }

  private def openBigView() {
    WindowManager.removeSmallView()
    WindowManager.createBigView()
  }

  private def getStatusBarHeight(): Int = {
    if (mStatusBarHeight == -1) {
      try {
        val c = Class.forName("com.android.internal.R$dimen")
        val o = c.newInstance()
        val x: Int = c.getField("status_bar_height").get(o)
        mStatusBarHeight = getResources().getDimensionPixelSize(x)
      } catch (Exception e) {
        e.printStackTrace()
      }
    }
    return mStatusBarHeight
  }
}
