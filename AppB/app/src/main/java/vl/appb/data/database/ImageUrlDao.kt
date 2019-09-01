package vl.appb.data.database

import android.database.Cursor
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import vl.appb.data.constants.COLUMN_ID
import vl.appb.data.constants.COLUMN_STATUS

@Dao
interface ImageUrlDao {

    @RawQuery
    fun rawQuery(query: SupportSQLiteQuery): Cursor

    @Insert
    fun insert(imageUrl: ImageUrl)

    @Query("UPDATE $TABLE_NAME_IMAGE_URLS SET $COLUMN_STATUS = :status WHERE $COLUMN_ID = :id")
    fun update(status: Int, id: Long)

    @Query("DELETE FROM $TABLE_NAME_IMAGE_URLS WHERE $COLUMN_ID = :id")
    fun delete(id: Long)
}