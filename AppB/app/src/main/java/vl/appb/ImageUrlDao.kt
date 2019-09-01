package vl.appb

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ImageUrlDao {

    @Query("SELECT * FROM image_urls")
    fun getAllEntries(): Cursor

    @RawQuery
    fun rawQuery(query: SupportSQLiteQuery): Cursor

    @Insert
    fun insert(imageUrl: ImageUrl)

    @Query("UPDATE $TABLE_NAME_IMAGE_URLS SET $COLUMN_STATUS = :status WHERE $COLUMN_ID = :id")
    fun update(status: Int, id: Long)

    @Query("DELETE FROM $TABLE_NAME_IMAGE_URLS WHERE $COLUMN_ID = :id")
    fun delete(id: Long)
}