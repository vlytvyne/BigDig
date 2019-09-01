package vl.appa.ui.main.history

import android.database.Cursor
import android.net.Uri
import vl.appa.data.constants.*
import vl.appa.data.filters.FilterImageUrlsStateHolder
import vl.appa.data.other.ContentResolverWrapper
import java.lang.StringBuilder

class HistoryPresenter(private val view: HistoryView,
					   private val contentResolver: ContentResolverWrapper) {

	private var cursorToClose: Cursor? = null

	init {
		contentResolver.registerContentObserver(Uri.parse(IMAGE_URLS_CONTENT_URI), ::refreshData)
	}

	private val selectionString: String?
		get() = if (FilterImageUrlsStateHolder.countDisplayTypes > 0) {
				val builder = StringBuilder()
				builder.append("WHERE $COLUMN_STATUS IN (")
				if (FilterImageUrlsStateHolder.displayDownloaded) builder.append("$STATUS_DOWNLOADED,")
				if (FilterImageUrlsStateHolder.displayError) builder.append("$STATUS_ERROR,")
				if (FilterImageUrlsStateHolder.displayUndefined) builder.append("$STATUS_UNDEFINED,")
				builder.deleteCharAt(builder.length - 1)
				builder.append(")")
				builder.toString()
			} else {
				null
			}

	private val orderString
		get() =
			if (FilterImageUrlsStateHolder.startFromNewest) {
				"$COLUMN_TIMESTAMP DESC"
			} else {
				"$COLUMN_TIMESTAMP ASC"
			}

	fun refreshData() {
		cursorToClose = contentResolver.query(
				Uri.parse(IMAGE_URLS_CONTENT_URI),
				null,
				selectionString,
				null,
				orderString
		)
		if (cursorToClose != null) {
			view.setHistoryListData(cursorToClose!!)
		}
	}

	fun clearResources() {
		cursorToClose?.close()
	}


}
