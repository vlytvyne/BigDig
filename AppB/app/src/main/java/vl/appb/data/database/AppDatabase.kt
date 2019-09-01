package vl.appb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageUrl::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun imageUrlDao(): ImageUrlDao
}