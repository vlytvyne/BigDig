package vl.appb

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

const val AUTHORITY = "vl.appb.provider"

const val IMAGE_URLS_PATH = "imageUrls"

const val IMAGE_URLS_CONTENT_URI = "content://$AUTHORITY/$IMAGE_URLS_PATH"

const val IMAGE_URLS_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$IMAGE_URLS_PATH"

const val URI_IMAGE_URLS = 1

class AppBContentProvider: ContentProvider() {

	val imagesUrlsContentUri = Uri.parse(IMAGE_URLS_CONTENT_URI)

	val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
		addURI(AUTHORITY, IMAGE_URLS_PATH, URI_IMAGE_URLS)
	}

	override fun onCreate(): Boolean {
		return true
	}

	override fun query(uri: Uri,
					   projection: Array<out String>?,
					   selection: String?,
					   selectionArgs: Array<out String>?,
					   sortOrder: String?): Cursor? =
		if (uriMatcher.match(uri) == URI_IMAGE_URLS) {
			val cursor = OfflineRepository.getAllEntries()
			contentResolver = context!!.contentResolver
			cursor
		} else {
			null
		}

	override fun insert(uri: Uri, values: ContentValues?): Uri? {
		return null
	}

	override fun update(uri: Uri,
						values: ContentValues?,
						selection: String?,
						selectionArgs: Array<out String>?) = 0

	override fun delete(uri: Uri,
						selection: String?,
						selectionArgs: Array<out String>?) = 0

	override fun getType(uri: Uri): String? =
		if (uriMatcher.match(uri) == URI_IMAGE_URLS) {
			IMAGE_URLS_TYPE
		} else {
			null
		}

	companion object {
		var contentResolver: ContentResolver? = null
	}
}