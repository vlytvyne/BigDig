package vl.appb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.schedulers.Schedulers
import vl.appb.AppB.Companion.database
import kotlin.concurrent.thread

class DirectOpenActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_direct_open)

		thread {
			val cursor = OfflineRepository.getAllEntries()
			cursor.moveToFirst()
			do {
				val data = cursor.getString(cursor.getColumnIndex("url"))
				Log.d("TAG", "$data\n")
			} while (cursor.moveToNext())
		}
	}
}
