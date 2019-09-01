package vl.appb.data.other

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.sqlite.db.SimpleSQLiteQuery
import vl.appb.data.constants.AUTHORITY
import vl.appb.data.constants.IMAGE_URLS_PATH
import vl.appb.data.repositories.OfflineRepository
import java.lang.StringBuilder

const val IMAGE_URLS_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$IMAGE_URLS_PATH"

const val URI_IMAGE_URLS_ID = 1

class AppBContentProvider: ContentProvider() {

	private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
		addURI(AUTHORITY, IMAGE_URLS_PATH, URI_IMAGE_URLS_ID)
	}

	override fun onCreate() = true

	override fun query(uri: Uri,
					   projection: Array<out String>?,
					   selection: String?,
					   selectionArgs: Array<out String>?,
					   sortOrder: String?): Cursor? {
		if (uriMatcher.match(uri) == URI_IMAGE_URLS_ID) {
			OfflineRepository.contentResolver = context!!.contentResolver
			OfflineRepository.databaseContext = context!!
			return OfflineRepository.rawQuery(buildQuery(selection, sortOrder))
		} else {
			return null
		}
	}

	private fun buildQuery(selection: String?,
						   sortOrder: String?): SimpleSQLiteQuery {
		val builder = StringBuilder()

		builder.append("SELECT * FROM image_urls ")
		if (selection != null) {
			builder.append("$selection ")
		}
		if (sortOrder != null) {
			builder.append("ORDER BY $sortOrder")
		}
		return SimpleSQLiteQuery(builder.toString())
	}

	override fun insert(uri: Uri, values: ContentValues?) = null

	override fun update(uri: Uri,
						values: ContentValues?,
						selection: String?,
						selectionArgs: Array<out String>?) = 0

	override fun delete(uri: Uri,
						selection: String?,
						selectionArgs: Array<out String>?) = 0

	override fun getType(uri: Uri): String? =
		if (uriMatcher.match(uri) == URI_IMAGE_URLS_ID) {
			IMAGE_URLS_TYPE
		} else {
			null
		}
}