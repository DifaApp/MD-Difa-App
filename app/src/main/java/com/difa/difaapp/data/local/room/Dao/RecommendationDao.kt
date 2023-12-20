package com.difa.difaapp.data.local.room.Dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.difa.difaapp.data.local.entity.Recommendation

@Dao
interface RecommendationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecommendation(recommendation: List<Recommendation>)

    @Query("SELECT * FROM recommendation")
    fun getAllRecommendation(): LiveData<List<Recommendation>>

    @Query("DELETE FROM recommendation")
    suspend fun deleteStory()
}