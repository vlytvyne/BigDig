package vl.appa

import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {

	lateinit var adapter: UrlsAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_history, container, false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)

		adapter = UrlsAdapter()
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
		if (adapter.cursor == null) {
			adapter.cursor = activity!!.contentResolver.query(
					Uri.parse(IMAGE_URLS_CONTENT_URI),
					null,
					null,
					null,
					null
			)
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.history, menu)
	}

	private fun runTest() {
		val cursor = activity!!.contentResolver.query(
			Uri.parse(IMAGE_URLS_CONTENT_URI),
			null,
			null,
			null,
			null
		)
		Log.d("TAG", cursor.count.toString())
		activity!!.contentResolver.registerContentObserver(Uri.parse(IMAGE_URLS_CONTENT_URI), true,
			object : ContentObserver(Handler()) {
				override fun onChange(selfChange: Boolean) {
					super.onChange(selfChange)
					Log.d("TAG", "onChange")
					//db changed
				}
			})
		while (cursor.moveToNext()) {
			val data = cursor.getString(cursor.getColumnIndex("url"))
			Log.d("TAG", "$data\n")
		}
		cursor.close()
	}

	companion object {

		fun newInstance() = HistoryFragment()
	}
}
