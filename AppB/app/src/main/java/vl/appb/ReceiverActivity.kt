package vl.appb

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_receiver.*
import vl.appb.AppB.Companion.database
import java.util.*

const val KEY_IMAGE_URL = "imageUrl"
const val STATUS_OK = 1
const val STATUS_ERROR = 2
const val STATUS_UNDEFINED = 3

class ReceiverActivity : AppCompatActivity() {

	val unitTimeStamp = Date().time / 1000
	lateinit var url: String

	private val loadingListener = object : RequestListener<Drawable> {
		override fun onResourceReady(resource: Drawable?,
									 model: Any?,
									 target: Target<Drawable>?,
									 dataSource: DataSource?,
									 isFirstResource: Boolean): Boolean {
			val imageUrl = ImageUrl(url, STATUS_OK, unitTimeStamp)
			Single.fromCallable {database.imageUrlDao().insert(imageUrl)}
				.subscribeOn(Schedulers.io())
				.subscribe()
			image.setImageDrawable(resource)
			return true
		}

		override fun onLoadFailed(
			e: GlideException?,
			model: Any?,
			target: Target<Drawable>?,
			isFirstResource: Boolean): Boolean {
			val imageUrl = ImageUrl(url, STATUS_ERROR, unitTimeStamp)
			Single.fromCallable {database.imageUrlDao().insert(imageUrl)}
				.subscribeOn(Schedulers.io())
				.subscribe()
			return true
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_receiver)

		url = intent.getStringExtra(KEY_IMAGE_URL)
		textUrl.text = url

		Glide.with(this)
			.load(url)
			.listener(loadingListener)
			.into(image)
	}
}
