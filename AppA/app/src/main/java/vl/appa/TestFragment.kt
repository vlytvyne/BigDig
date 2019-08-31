package vl.appa

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {

	override fun onCreateView(inflater: LayoutInflater,
							  container: ViewGroup?,
							  savedInstanceState: Bundle?) =
		inflater.inflate(R.layout.fragment_test, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		button.setOnClickListener {
			if (editTextUrl.text.isNullOrBlank()) {
				textInputLayout.error = "This field can't be blank"
			} else {
				showImage(editTextUrl.text.toString())
			}
		}
	}

	private fun showImage(url: String) {
		val intent = Intent()
		intent.action = INTENT_ACTION_SHOW_IMAGE
		intent.putExtra(INTENT_KEY_IMAGE_URL, url)
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
		startActivity(intent)
	}

	companion object {

		fun newInstance() = TestFragment()
	}
}
