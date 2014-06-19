package com.cocgenius.scala

import org.scaloid.common._

import android.app.ActivityManager
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import android.view.WindowManager.LayoutParams

object WindowManager {
    private var smallView: FloatWindowSmallView = null
    private var bigView: FloatWindowBigView = null
    private var windowManager: WindowManager = null
    private var activityManager: ActivityManager = null

    def getWindowManager()(implicit context: SContext): WindowManager = {
        if (windowManager == null) {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE)
        }
        windowManager
    }
    def getActivityManager()(implicit context: SContext): ActivityManager = {
        if (activityManager == null) {
            activityManager = context.getSystemService(Context.ACTIVITY_SERVICE)
        }
        activityManager
    }

    def createSmallView()(implicit context: SContext) {
        val lWindowManager = getWindowManager
        val lScreenWidth = lWindowManager.getDefaultDisplay().getWidth();
        val lScreenHeight = lWindowManager.getDefaultDisplay().getHeight();
        if (smallView == null) {
            smallView = new FloatWindowSmallView(context)
            val params = new LayoutParams
            params.type = LayoutParams.TYPE_PHONE
            params.format = LayoutParams.RGBA_8888
            params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_TOUCHABLE
            params.gravity = Gravity.LEFT | Gravity.TOP
            params.width = FloatWindowSmallView.viewWidth
            params.height = FloatWindowSmallView.viewHeight
            params.x = lScreenWidth
            params.y = lScreenHeight / 2
            smallView.setParams(params)
            lWindowManager.addView(smallView, params)
        }
    }
    def removeSmallView()(implicit context: SContext) {
        if (smallView != null) {
            getWindowManager.removeView(smallView)
            smallView = null
        }
    }

    def createBigView()(implicit context: SContext) {
        val lWindowManager = getWindowManager
        val lScreenWidth = lWindowManager.getDefaultDisplay().getWidth();
        val lScreenHeight = lWindowManager.getDefaultDisplay().getHeight();
        if (bigView == null) {
            bigView = new FloatWindowBigView(context)
            params = new LayoutParams
            params.type = LayoutParams.TYPE_PHONE
            params.format = LayoutParams.RGBA_8888
            params.gravity = Gravity.LEFT | Gravity.TOP
            params.x = (lScreenWidth - FloatWindowBigView.viewWidth) / 2
            params.y = (lScreenHeight - FloatWindowBigView.viewHeight) / 2
            params.width = FloatWindowBigView.viewWidth
            params.height = FloatWindowBigView.viewHeight
            bigView.setParams(params)
            lWindowManager.addView(bigView, params)
        }
    }
    def removeBigView()(implicit context: SContext) {
        if (bigView != null) {
            getWindowManager.removeView(bigView)
            bigView = null
        }
    }

    def isWindowShowing(): Boolean = smallView != null || bigView != null
}
