package vl.appb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_direct_open.*

class DirectOpenActivity : AppCompatActivity() {

	var secondsLeft = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_direct_open)
	}

	override fun onResume() {
		super.onResume()
		val handler = Handler()

		val tickTimer = object : Runnable {
			override fun run() {
				secondsLeft--
				textSecondsLeft.text = secondsLeft.toString()
				if (secondsLeft == 0) {
					finishAndRemoveTask()
				} else {
					handler.postDelayed(this, 1000)
				}
			}
		}

		handler.postDelayed(tickTimer, 1000)
	}
}
