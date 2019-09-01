package vl.appb

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast

const val SECONDS_BEFORE_REMOVE: Long = 15

class RemoveImageFromDBService : IntentService("removeImage") {

	var handler = Handler()

	override fun onHandleIntent(intent: Intent?) {
		intent?.let {
			Log.d("TAG", "onHandleIntent")
			val id = intent.getLongExtra(INTENT_KEY_IMAGE_ID, 0)
			val url = intent.getStringExtra(INTENT_KEY_IMAGE_URL)
			Thread.sleep(SECONDS_BEFORE_REMOVE * 1000)
			handler.post {
				Toast.makeText(applicationContext, "Image url: $url is removed from DB", Toast.LENGTH_LONG).show()
			}
			OfflineRepository.delete(id)
		}
	}

	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		return super.onStartCommand(intent, flags, startId)
	}

	override fun onTaskRemoved(rootIntent: Intent?) {
		super.onTaskRemoved(rootIntent)

	}

	override fun onBind(intent: Intent): IBinder {
		TODO("Return the communication channel to the service.")
	}

	override fun onDestroy() {
		super.onDestroy()
	}
}
