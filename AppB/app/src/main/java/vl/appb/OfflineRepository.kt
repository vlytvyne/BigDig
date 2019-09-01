package vl.appb

import android.content.ContentResolver
import android.net.Uri
import androidx.sqlite.db.SupportSQLiteQuery
import vl.appb.AppB.Companion.database
import kotlin.concurrent.thread

object OfflineRepository {

	var contentResolver: ContentResolver? = null

	fun getAllEntries() =
		database.imageUrlDao().getAllEntries()

	fun rawQuery(query: SupportSQLiteQuery) =
		database.imageUrlDao().rawQuery(query)

	fun insertLoadedUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_DOWNLOADED, unixTimeStamp )

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

	fun update(status: Int, id: Long) {
		thread {
			database.imageUrlDao().update(status, id)
			contentResolver?.notifyChange(Uri.parse(IMAGE_URLS_CONTENT_URI), null)
		}
	}

	fun delete(id: Long) {
		thread {
			database.imageUrlDao().delete(id)
			contentResolver?.notifyChange(Uri.parse(IMAGE_URLS_CONTENT_URI), null)
		}
	}

}