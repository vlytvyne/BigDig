package vl.appb.ui.directopen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_direct_open.*
import vl.appb.R

class DirectOpenActivity : AppCompatActivity() {

	var secondsLeft = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_direct_open)
	}

	override fun onResume() {
		super.onResume()
		textSecondsLeft.text = secondsLeft.toString()

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
