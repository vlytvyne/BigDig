package vl.appb

import android.database.Cursor
import android.net.Uri
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import vl.appb.AppB.Companion.database
import vl.appb.AppBContentProvider.Companion.contentResolver
import kotlin.concurrent.thread

const val STATUS_OK = 1
const val STATUS_ERROR = 2
const val STATUS_UNDEFINED = 3

object OfflineRepository {

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
		thread { update(imageUrl) }
	}

	fun delete(imageUrl: ImageUrl) {
		thread { delete(imageUrl) }
	}

//	private fun decorateCall(func: ImageUrlDao.() -> Unit) {
//		Single.fromCallable { func }
//			.subscribeOn(Schedulers.io())
//			.subscribe()
//	}
}