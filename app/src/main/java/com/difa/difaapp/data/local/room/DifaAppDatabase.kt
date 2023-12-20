package com.difa.difaapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.difa.difaapp.data.local.entity.Recommendation
import com.difa.difaapp.data.local.room.Dao.RecommendationDao

@Database(
    entities = [Recommendation::class],
    version = 1,
    exportSchema = true
)
abstract class DifaAppDatabase : RoomDatabase(){
    abstract fun recommendationDao(): RecommendationDao
    companion object{
        @Volatile
        private var INSTANCE: DifaAppDatabase? = null
        fun getInstance(context: Context): DifaAppDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DifaAppDatabase::class.java, "Difa_app.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
    }
}