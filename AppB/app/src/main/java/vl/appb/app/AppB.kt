package vl.appb.app

import android.app.Application
import vl.appb.data.repositories.OfflineRepository

class AppB: Application() {

	override fun onCreate() {
		super.onCreate()
		OfflineRepository.databaseContext = applicationContext
	}
}