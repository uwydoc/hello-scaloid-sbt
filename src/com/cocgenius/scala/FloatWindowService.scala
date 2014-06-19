package com.cocgenius.scala

import org.scaloid.common._

import java.util.Timer

import android.content.Intent
import android.os.IBinder

class FloatWindowService extends SService {
    private var timer: Timer = null

    def onBind(intent: Intent): IBinder = null
}
