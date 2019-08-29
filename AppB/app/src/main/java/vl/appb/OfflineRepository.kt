package vl.appb

import android.database.Cursor
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import vl.appb.AppB.Companion.database

const val STATUS_OK = 1
const val STATUS_ERROR = 2
const val STATUS_UNDEFINED = 3

object OfflineRepository {

	fun getAllEntries(): Cursor {
		return database.imageUrlDao().getAllEntries()
	}

	fun insertLoadedUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_OK, unixTimeStamp )
		decorateCall {database.imageUrlDao().insert(imageUrl)}
	}

	fun insertErrorUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_ERROR, unixTimeStamp)
		decorateCall { database.imageUrlDao().insert(imageUrl) }
	}

	fun update(imageUrl: ImageUrl) {
		decorateCall { database.imageUrlDao().update(imageUrl) }
	}

	fun delete(imageUrl: ImageUrl) {
		decorateCall { database.imageUrlDao().delete(imageUrl) }
	}

	private fun decorateCall(func: ImageUrlDao.() -> Unit) {
		Single.fromCallable { func }
			.subscribeOn(Schedulers.io())
			.subscribe()
	}
}