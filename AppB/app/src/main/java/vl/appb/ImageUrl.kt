package vl.appb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_urls")
data class ImageUrl(@ColumnInfo(name = COLUMN_URL) val url: String,
					@ColumnInfo(name = COLUMN_STATUS) val status: Int,
					@ColumnInfo(name = COLUMN_TIMESTAMP) val unixTimeStamp: Long,
					@ColumnInfo(name = COLUMN_ID) @PrimaryKey(autoGenerate = true) val id: Long = 0)