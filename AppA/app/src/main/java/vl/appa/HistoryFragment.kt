package vl.appa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

class HistoryFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_history, container, false)

	companion object {

		fun newInstance() = HistoryFragment()
	}
}