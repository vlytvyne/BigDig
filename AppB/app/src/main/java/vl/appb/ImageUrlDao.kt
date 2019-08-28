package vl.appb

import androidx.room.*
import io.reactivex.Single

@Dao
interface ImageUrlDao {

    @Query("SELECT * FROM image_urls")
    fun getAllEntries(): Single<List<ImageUrl>>

    @Insert
    fun insert(imageUrl: ImageUrl)

    @Update
    fun update(imageUrl: ImageUrl)

    @Delete
    fun delete(imageUrl: ImageUrl)
}