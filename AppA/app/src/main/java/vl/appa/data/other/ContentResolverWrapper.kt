package vl.appa.data.other

import android.database.Cursor
import android.net.Uri

interface ContentResolverWrapper {

	fun query(uri: Uri, projection: Array<String>?,
			  selection: String?,
			  selectionArgs: Array<String>?,
			  sortOrder: String?): Cursor?

	fun registerContentObserver(uri: Uri, onChange: () -> Unit)
}