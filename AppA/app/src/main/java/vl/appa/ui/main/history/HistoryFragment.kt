package vl.appa.ui.main.history

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import vl.appa.ui.main.test.filter.FilterImageUrlsActivity
import vl.appa.R
import vl.appa.app.AppA.Companion.appContext
import vl.appa.data.constants.*
import vl.appa.data.other.ContentResolverWrapper
import vl.appa.recyclers.adapters.UrlsAdapter

class HistoryFragment : Fragment(), HistoryView {

	lateinit var adapter: UrlsAdapter
	lateinit var presenter: HistoryPresenter

	private val contentResolverWrapper = object : ContentResolverWrapper {

		override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
			return activity!!.contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
		}

		override fun registerContentObserver(uri: Uri, onChange: () -> Unit) {
			activity!!.contentResolver.registerContentObserver(uri, true,
					object : ContentObserver(Handler()) {
						override fun onChange(selfChange: Boolean) {
							super.onChange(selfChange)
							onChange.invoke()
						}
					}
			)
		}
	}

	private val onUrlClickListener: ((String, Int, Long) -> Unit) = {
		url, status, id ->
		val intent = Intent()
		intent.action = INTENT_ACTION_SHOW_IMAGE
		intent.putExtra(INTENT_KEY_IMAGE_URL, url)
		intent.putExtra(INTENT_KEY_IMAGE_STATUS, status)
		intent.putExtra(INTENT_KEY_IMAGE_ID, id)
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
		startActivity(intent)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_history, container, false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)

		presenter = HistoryPresenter(this, contentResolverWrapper)

		adapter = UrlsAdapter()
		adapter.onUrlClickListener = onUrlClickListener
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupRecycler()
	}

	override fun onResume() {
		super.onResume()
		presenter.refreshData()
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.history, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == R.id.toolbar_filter) {
			FilterImageUrlsActivity.start(activity!!)
			return true
		} else {
			return super.onOptionsItemSelected(item)
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		presenter.clearResources()
	}

	override fun setHistoryListData(cursor: Cursor) {
		adapter.cursor = cursor
	}

	private fun setupRecycler() {
		recyclerView.adapter = adapter
		val listDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
		listDecoration.setDrawable(activity!!.getDrawable(R.drawable.list_divider)!!)
		recyclerView.addItemDecoration(listDecoration)
	}

	companion object {

		fun newInstance() = HistoryFragment()

		val tabTitle = appContext.getString(R.string.tab_title_history)
	}
}
