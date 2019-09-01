package vl.appb.services

import android.app.IntentService
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import vl.appb.R
import vl.appb.data.constants.INTENT_KEY_IMAGE_ID
import vl.appb.data.constants.INTENT_KEY_IMAGE_URL
import vl.appb.data.repositories.OfflineRepository

const val SECONDS_BEFORE_REMOVE: Long = 15

class RemoveUrlFromDBService : IntentService("removeImage") {

	var handler = Handler()

	override fun onHandleIntent(intent: Intent?) {
		intent?.let {
			val id = intent.getLongExtra(INTENT_KEY_IMAGE_ID, 0)
			val url = intent.getStringExtra(INTENT_KEY_IMAGE_URL)
			Thread.sleep(SECONDS_BEFORE_REMOVE * 1000)
			handler.post {
				Toast.makeText(applicationContext,
					applicationContext.getString(R.string.toast_image_removed_from_db, url),
					Toast.LENGTH_LONG).show()
			}
			OfflineRepository.delete(id)
		}
	}

}
