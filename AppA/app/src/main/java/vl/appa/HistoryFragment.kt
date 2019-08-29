package vl.appa

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.ViewGroup

const val URI = "content://vl.appb.provider/imageUrls"

class HistoryFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_history, container, false)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)

		Thread(Runnable {
			val cursor = activity!!.contentResolver.query(Uri.parse(URI), null, null, null, null)
			Log.d("TAG", cursor.count.toString())
			cursor.close()
		}).start()
	}
	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		inflater?.inflate(R.menu.history, menu)
	}

	companion object {

		fun newInstance() = HistoryFragment()
	}
}
