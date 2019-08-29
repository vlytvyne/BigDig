package vl.appb

import android.database.Cursor
import androidx.room.*
import io.reactivex.Single

@Dao
interface ImageUrlDao {

    @Query("SELECT * FROM image_urls")
    fun getAllEntries(): Cursor

    @Insert
    fun insert(imageUrl: ImageUrl)

    @Update
    fun update(imageUrl: ImageUrl)

    @Delete
    fun delete(imageUrl: ImageUrl)
}