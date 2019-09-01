package vl.appa.ui.main.test.filter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_filter.*
import vl.appa.R

class FilterImageUrlsActivity : AppCompatActivity(), FilterImageUrlsView {

	override var selectionStateFromNewest: Boolean
		get() = switchNewestFirst.isChecked
		set(value) { switchNewestFirst.isChecked = value }

	override var selectionStateDisplayDownloaded: Boolean
		get() = checkBoxDownloaded.isChecked
		set(value) { checkBoxDownloaded.isChecked = value }

	override var selectionStateDisplayError: Boolean
		get() = checkBoxError.isChecked
		set(value) { checkBoxError.isChecked = value }

	override var selectionStateDisplayUndefined: Boolean
		get() = checkBoxUndefined.isChecked
		set(value) { checkBoxUndefined.isChecked = value }

	lateinit var presenter: FilterImageUrlsPresenter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_filter)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

		presenter = FilterImageUrlsPresenter(this)

		buttonResetFilter.setOnClickListener { presenter.resetFilter() }
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.apply, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == R.id.toolbar_apply) {
			presenter.apply()
			return true
		} else {
			return super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		onBackPressed()
		return true
	}

	override fun closeView() {
		onBackPressed()
	}

	companion object {

		fun start(activity: Activity) {
			val intent = Intent(activity, FilterImageUrlsActivity::class.java)
			activity.startActivity(intent)
		}
	}
}
