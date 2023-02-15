package com.example.allmyreview.addReview

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReviewDao {
    @Insert
    suspend fun insert(review: Review)

    @Update
    suspend fun update(review: Review)

    @Query("SELECT * FROM Review") // 테이블의 모든 값
    suspend fun getAll(): List<Review>

    @Query("SELECT * FROM Review WHERE code = :code")
    suspend fun getReview(code: Int) : Review

    @Query("DELETE FROM Review WHERE code = :code")
    suspend fun deleteUserByName(code: Int)

}
