package vl.appb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_urls")
data class ImageUrl(val url: String,
					val status: Int,
					val unitTimeStamp: Long,
					@PrimaryKey(autoGenerate = true) val id: Long = 0)