package vl.appb.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vl.appb.data.constants.COLUMN_ID
import vl.appb.data.constants.COLUMN_STATUS
import vl.appb.data.constants.COLUMN_TIMESTAMP
import vl.appb.data.constants.COLUMN_URL

const val TABLE_NAME_IMAGE_URLS = "image_urls"

@Entity(tableName = TABLE_NAME_IMAGE_URLS)
data class ImageUrl(@ColumnInfo(name = COLUMN_URL) val url: String,
					@ColumnInfo(name = COLUMN_STATUS) val status: Int,
					@ColumnInfo(name = COLUMN_TIMESTAMP) val unixTimeStamp: Long,
					@ColumnInfo(name = COLUMN_ID) @PrimaryKey(autoGenerate = true) val id: Long = 0)