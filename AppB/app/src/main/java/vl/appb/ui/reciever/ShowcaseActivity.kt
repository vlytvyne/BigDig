package vl.appb.ui.reciever

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_receiver.*
import vl.appb.R
import vl.appb.data.constants.*
import vl.appb.data.repositories.OfflineRepository
import vl.appb.services.RemoveUrlFromDBService
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.*

@SuppressLint("SdCardPath")
const val SAVE_DEVICE_LOCATION = "/sdcard/BIGDIG/test/B"
const val RC_WRITE_EXTERNAL_PERMISSION = 1

class ReceiverActivity : AppCompatActivity() {

	val unixTimeStamp = Date().time / 1000
	lateinit var url: String
	var fromHistory: Boolean = false
	var status: Int = 0
	var id: Long = 0

	var imageToSave: Bitmap? = null

	private val loadingListener = object : RequestListener<Drawable> {
		override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
			onLoadingSuccess(resource)
			return true
		}

		override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
			onLoadingFailed()
			return true
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_receiver)

		if (intent.action == INTENT_ACTION_SHOW_IMAGE) {
			fetchIntentValues()
			loadImage()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int,
											permissions: Array<out String>,
											grantResults: IntArray) {
		if (requestCode == RC_WRITE_EXTERNAL_PERMISSION) {
			if (isWriteExternalPermissionGranted(grantResults)) {
				saveImageOnDisk()
			}
		}
	}

	private fun isWriteExternalPermissionGranted(grantResults: IntArray) =
		(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)

	private fun saveImageOnDisk() {
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
				performSaveOnDisk()
			} else {
				if (ActivityCompat.shouldShowRequestPermissionRationale(this@ReceiverActivity,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					ActivityCompat.requestPermissions(this@ReceiverActivity,
						arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
						RC_WRITE_EXTERNAL_PERMISSION
					)
				}
			}
		}
	}

	private fun performSaveOnDisk() {
		try {
			val imageFileName = "image_$id.jpeg"
			val imageFile = File(SAVE_DEVICE_LOCATION, imageFileName)
			imageFile.parentFile!!.mkdirs()
			imageFile.createNewFile()
			val fos = FileOutputStream(imageFile)
			imageToSave?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
			fos.close()
		} catch (e: Exception) {
			//handle error
		}
	}

	private fun fetchIntentValues() {
		url = intent.getStringExtra(INTENT_KEY_IMAGE_URL)!!
		fromHistory = intent.hasExtra(INTENT_KEY_IMAGE_STATUS)
		if (fromHistory) {
			status = intent.getIntExtra(INTENT_KEY_IMAGE_STATUS, 0)
			id = intent.getLongExtra(INTENT_KEY_IMAGE_ID, 0)
		}
	}

	private fun loadImage() {
		textUrl.text = url

		Glide.with(this)
			.load(url)
			.listener(loadingListener)
			.into(image)
	}

	private fun deleteFromDb(id: Long) {
		val intent = Intent(this, RemoveUrlFromDBService::class.java)
		intent.putExtra(INTENT_KEY_IMAGE_ID, id)
		intent.putExtra(INTENT_KEY_IMAGE_URL, url)
		startService(intent)
	}

	private fun onLoadingSuccess(loadedImage: Drawable) {
		image.setImageDrawable(loadedImage)
		if (fromHistory) {
			if (status == STATUS_DOWNLOADED) {
				imageToSave = (loadedImage as BitmapDrawable).bitmap
				saveImageOnDisk()
				deleteFromDb(id)
			} else {
				OfflineRepository.update(STATUS_DOWNLOADED, id)
			}
		} else {
			OfflineRepository.insertLoadedUrl(url, unixTimeStamp)
		}
	}

	private fun onLoadingFailed() {
		if (fromHistory) {
			OfflineRepository.update(STATUS_ERROR, id)
		} else {
			OfflineRepository.insertErrorUrl(url, unixTimeStamp)
		}
	}

}
