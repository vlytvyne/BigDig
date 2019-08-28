package vl.appb

import android.app.Application
import androidx.room.Room

class AppB: Application() {

	override fun onCreate() {
		super.onCreate()
		database = Room.databaseBuilder(
			applicationContext,
			AppDatabase::class.java,
			"database")
			.build()
	}

	companion object {
		lateinit var database: AppDatabase
	}
}