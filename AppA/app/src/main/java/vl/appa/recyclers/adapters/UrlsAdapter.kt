package vl.appa.recyclers.adapters

import android.database.Cursor
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vl.appa.recyclers.viewholders.UrlVH
import vl.appa.data.constants.COLUMN_ID
import vl.appa.data.constants.COLUMN_STATUS
import vl.appa.data.constants.COLUMN_TIMESTAMP
import vl.appa.data.constants.COLUMN_URL
import vl.appa.data.models.ImageUrl
import java.text.SimpleDateFormat
import java.util.*

class UrlsAdapter: RecyclerView.Adapter<UrlVH>() {

	var cursor: Cursor? = null
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	var onUrlClickListener: ((String, Int, Long) -> Unit)? = null

	private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		UrlVH.create(parent)

	override fun getItemCount() =
		cursor?.count ?: 0

	override fun onBindViewHolder(holder: UrlVH, position: Int) {
		cursor?.let {
			cursor ->
			cursor.moveToPosition(position)
			val url = cursor.getString(cursor.getColumnIndex(COLUMN_URL))
			val status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))
			val unixTimeStamp = cursor.getLong(cursor.getColumnIndex(COLUMN_TIMESTAMP))
			val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
			val formattedDate = dateFormatter.format(unixTimeStamp * 1000)
			val imageUrl = ImageUrl(url, status, formattedDate)
			holder.bind(imageUrl)
			holder.itemView.setOnClickListener { onUrlClickListener?.invoke(url, status, id) }
		}
	}

}