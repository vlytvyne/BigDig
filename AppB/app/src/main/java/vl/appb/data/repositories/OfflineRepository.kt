package vl.appb.data.repositories

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteQuery
import vl.appb.data.constants.IMAGE_URLS_CONTENT_URI
import vl.appb.data.constants.STATUS_DOWNLOADED
import vl.appb.data.constants.STATUS_ERROR
import vl.appb.data.database.AppDatabase
import vl.appb.data.database.ImageUrl
import kotlin.concurrent.thread

@SuppressLint("StaticFieldLeak")
object OfflineRepository {

	private val uriImageUrls = Uri.parse(IMAGE_URLS_CONTENT_URI)

	var contentResolver: ContentResolver? = null
	lateinit var databaseContext: Context

	val database: AppDatabase by lazy {
		Room.databaseBuilder(
			databaseContext,
			AppDatabase::class.java,
			"database")
			.build()
	}

	fun rawQuery(query: SupportSQLiteQuery) =
		database.imageUrlDao().rawQuery(query)

	fun insertLoadedUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_DOWNLOADED, unixTimeStamp)

		thread {
			database.imageUrlDao().insert(imageUrl)
			contentResolver?.notifyChange(uriImageUrls, null)
		}
	}

	fun insertErrorUrl(url: String, unixTimeStamp: Long) {
		val imageUrl = ImageUrl(url, STATUS_ERROR, unixTimeStamp)

		thread {
			database.imageUrlDao().insert(imageUrl)
			contentResolver?.notifyChange(uriImageUrls, null)
		}

	}

	fun update(status: Int, id: Long) {
		thread {
			database.imageUrlDao().update(status, id)
			contentResolver?.notifyChange(uriImageUrls, null)
		}
	}

	fun delete(id: Long) {
		thread {
			database.imageUrlDao().delete(id)
			contentResolver?.notifyChange(uriImageUrls, null)
		}
	}

}