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

    @Update
    fun update(imageUrl: ImageUrl)

    @Delete
    fun delete(imageUrl: ImageUrl)
}