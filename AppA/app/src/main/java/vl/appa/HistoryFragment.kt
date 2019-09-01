package vl.appa

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import java.lang.StringBuilder

class HistoryFragment : Fragment() {

	lateinit var adapter: UrlsAdapter
	lateinit var cursorToClose: Cursor

	private val selectionString: String?
		get() {
			if (FilterStateHolder.countDisplayTypes > 0) {
				val builder = StringBuilder()
				builder.append("WHERE $COLUMN_STATUS IN (")
				if (FilterStateHolder.displayDownloaded) builder.append("$STATUS_DOWNLOADED,")
				if (FilterStateHolder.displayError) builder.append("$STATUS_ERROR,")
				if (FilterStateHolder.displayUndefined) builder.append("$STATUS_UNDEFINED,")
				builder.deleteCharAt(builder.length - 1)
				builder.append(")")
				return builder.toString()
			} else {
				return null
			}
		}

	private val orderString
		get() =
			if (FilterStateHolder.startFromNewest) {
				"$COLUMN_TIMESTAMP DESC"
			} else {
				"$COLUMN_TIMESTAMP ASC"
			}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_history, container, false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)

		adapter = UrlsAdapter()
		adapter.onUrlClickListener = {
			url, status, id ->
			val intent = Intent()
			intent.action = INTENT_ACTION_SHOW_IMAGE
			intent.putExtra(INTENT_KEY_IMAGE_URL, url)
			intent.putExtra(INTENT_KEY_IMAGE_STATUS, status)
			intent.putExtra(INTENT_KEY_IMAGE_ID, id)
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
		}
		activity!!.contentResolver.registerContentObserver(Uri.parse(IMAGE_URLS_CONTENT_URI), true,
			object : ContentObserver(Handler()) {
				override fun onChange(selfChange: Boolean) {
					super.onChange(selfChange)
					adapter.cursor = performQuery()
				}
			})
//		runTest()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		recyclerView.adapter = adapter
		val listDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
		listDecoration.setDrawable(activity!!.getDrawable(R.drawable.list_divider)!!)
		recyclerView.addItemDecoration(listDecoration)
	}

	override fun onResume() {
		super.onResume()
		adapter.cursor = performQuery()
		Log.d("TAG", FilterStateHolder.toString())
	}

	private fun performQuery(): Cursor? {
		cursorToClose = activity!!.contentResolver.query(
			Uri.parse(IMAGE_URLS_CONTENT_URI),
			null,
			selectionString,
			null,
			orderString
		)
		return cursorToClose
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.history, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == R.id.toolbar_filter) {
			FilterActivity.start(activity!!)
			return true
		} else {
			return super.onOptionsItemSelected(item)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		cursorToClose.close()
	}

//	private fun runTest() {
//		val cursor = activity!!.contentResolver.query(
//			Uri.parse(IMAGE_URLS_CONTENT_URI),
//			null,
//			null,
//			null,
//			null
//		)
//		Log.d("TAG", cursor.count.toString())
//		activity!!.contentResolver.registerContentObserver(Uri.parse(IMAGE_URLS_CONTENT_URI), true,
//			object : ContentObserver(Handler()) {
//				override fun onChange(selfChange: Boolean) {
//					super.onChange(selfChange)
//					Log.d("TAG", "onChange")
//					//db changed
//				}
//			})
//		while (cursor.moveToNext()) {
//			val data = cursor.getString(cursor.getColumnIndex("url"))
//			Log.d("TAG", "$data\n")
//		}
//		cursor.close()
//	}

	companion object {

		fun newInstance() = HistoryFragment()
	}
}
