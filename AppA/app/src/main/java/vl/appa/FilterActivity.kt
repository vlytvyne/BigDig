package vl.appa

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_filter)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
		setupFilterViews()

		buttonResetFilter.setOnClickListener { resetFilter() }
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.apply, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == R.id.toolbar_apply) {
			saveState()
			finish()
			return true
		} else {
			finish()
			return super.onOptionsItemSelected(item)
		}
	}

	private fun saveState() {
		with(FilterStateHolder) {
			startFromNewest = switchNewestFirst.isChecked
			displayDownloaded = checkBoxDownloaded.isChecked
			displayError = checkBoxError.isChecked
			displayUndefined = checkBoxUndefined.isChecked
		}
	}

	private fun setupFilterViews() {
		switchNewestFirst.isChecked = FilterStateHolder.startFromNewest
		checkBoxDownloaded.isChecked = FilterStateHolder.displayDownloaded
		checkBoxError.isChecked = FilterStateHolder.displayError
		checkBoxUndefined.isChecked = FilterStateHolder.displayUndefined
	}

	private fun resetFilter() {
		switchNewestFirst.isChecked = FilterStateHolder.DefaultState.startFromNewest
		checkBoxDownloaded.isChecked = FilterStateHolder.DefaultState.displayDownloaded
		checkBoxError.isChecked = FilterStateHolder.DefaultState.displayError
		checkBoxUndefined.isChecked = FilterStateHolder.DefaultState.displayUndefined
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, FilterActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
