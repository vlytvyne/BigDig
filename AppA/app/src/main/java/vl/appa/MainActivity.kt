package vl.appa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setSupportActionBar(toolbar)

		viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
		tabLayout.setupWithViewPager(viewPager)
	}

}

private class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

	override fun getItem(position: Int) =
		when (position) {
			0 -> TestFragment.newInstance()
			1 -> HistoryFragment.newInstance()
			else -> throw Exception("Out of items")
		}

	override fun getPageTitle(position: Int) =
		when (position) {
			0 -> "Test"
			1 -> "History"
			else -> throw Exception("Out of items")
		}


	override fun getCount() = 2
}