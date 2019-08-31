package vl.appb

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import vl.appb.AppB.Companion.database
import kotlin.concurrent.thread

object OfflineRepository {

	var contentResolver: ContentResolver? = null

	fun getAllEntries(): Cursor {
		return database.imageUrlDao().getAllEntries()
	}

	fun insertLoadedUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_OK, unixTimeStamp )

		thread {
			database.imageUrlDao().insert(imageUrl)
			contentResolver?.notifyChange(Uri.parse(IMAGE_URLS_CONTENT_URI), null)
		}
	}

	fun insertErrorUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_ERROR, unixTimeStamp)

		thread {
			database.imageUrlDao().insert(imageUrl)
			contentResolver?.notifyChange(Uri.parse(IMAGE_URLS_CONTENT_URI), null)
		}

	}

	fun update(imageUrl: ImageUrl) {
		thread { database.imageUrlDao().update(imageUrl) }
	}

	fun delete(imageUrl: ImageUrl) {
		thread { database.imageUrlDao().delete(imageUrl) }
	}

//	private fun decorateCall(func: ImageUrlDao.() -> Unit) {
//		Single.fromCallable { func }
//			.subscribeOn(Schedulers.io())
//			.subscribe()
//	}
}