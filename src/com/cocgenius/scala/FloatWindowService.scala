package com.cocgenius.scala

import org.scaloid.common._

import java.util.Timer

import android.os.IBinder

class FloatWindowService extends SService {
	def onBind(_: Intent) = null

	override def onStartCommand(intent: Intent, flags: Int, startId: Int): Int = {
		if (timer == null) {
			timer = new Timer();
			timer.scheduleAtFixedRate(new RefreshTask(), 0, 500)
		}
		super.onStartCommand(intent, flags, startId)
	}

	override def onDestroy(): Unit {
		super.onDestroy()
		timer.cancel()
		timer = null
	}

	def isHome(): Boolean = {
		val running_tasks = activityManager().getRunningTasks(1)
		val home_package_name = running_tasks.get(0).topActivity.getPackageName()
		return getHomes().contains(home_package_name)
	}

	def getHomes(): List[String] = {
		val resolve_infos = this.getPackageManager.queryIntentActivities(
			SIntent[Intent.MAIN_ACTIVITY],
			PackageManager.MATCH_DEFAULT_ONLY)
		for (info <- resolve_infos) yield info.activityInfo.packageName
	}

	class RefreshTask extends TimerTask {
		override def run(): Unit {
			if (isHome() && WindowManager.isWindowShowing()) {
				future {
					WindowManager.createSmallWindow(getApplicationContext())
				}
			} else if (!isHome() && WindowManager.isWindowShowing()) {
				future {
					//TODO
					;;
				}
			} else if (WindowManager.isWindowShowing()) {
				future {
					WindowManager.updateUsedPercent(getApplicationContext())
				}
			}
		}
	}

	private var timer: Timer = null
}
