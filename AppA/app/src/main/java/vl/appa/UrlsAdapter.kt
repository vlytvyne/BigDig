package vl.appa

import android.database.Cursor
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class UrlsAdapter(): RecyclerView.Adapter<UrlVH>() {

	var cursor: Cursor? = null

	private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		UrlVH.create(parent)

	override fun getItemCount() =
		cursor?.count ?: 0

	override fun onBindViewHolder(holder: UrlVH, position: Int) {
		cursor?.let {
			it.moveToPosition(position)
			holder.bind(extractImageUrlFromCursor(it))
		}
	}

	private fun extractImageUrlFromCursor(cursor: Cursor): ImageUrl {
		val url = cursor.getString(cursor.getColumnIndex(COLUMN_URL))
		val status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))
		val unixTimeStamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))
		val formattedDate = dateFormatter.format(unixTimeStamp * 1000)
		Log.d("TAG", "Unix: $unixTimeStamp, date: ${Date(unixTimeStamp).time / 1000}")
		return ImageUrl(url, status, formattedDate)
	}

}