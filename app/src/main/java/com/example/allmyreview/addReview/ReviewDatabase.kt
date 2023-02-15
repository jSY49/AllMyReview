package com.example.allmyreview.addReview

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Review::class), version = 1)
abstract class ReviewDatabase :RoomDatabase() {
    abstract fun reviewDao(): ReviewDao
    companion object {
        private var instance: ReviewDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ReviewDatabase? {
            if (instance == null) {
                synchronized(ReviewDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ReviewDatabase::class.java,
                        "user-database"
                    ).build()
                }
            }
            return instance
        }
    }
}

