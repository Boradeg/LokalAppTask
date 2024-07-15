package com.example.naukri.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface JobDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(favoriteJob: MyJob2)

    @Query("SELECT * FROM MyJob2 WHERE jobUniqueId = :jobUniqueId LIMIT 1")
    fun findByJobUniqueId(jobUniqueId: String): MyJob2?
    // Method to get all rows
    @Query("SELECT * FROM MyJob2")
     fun getAllJobs(): List<MyJob2>

}