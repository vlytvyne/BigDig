package vl.appb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.schedulers.Schedulers
import vl.appb.AppB.Companion.database

class DirectOpenActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_direct_open)
	}
}
