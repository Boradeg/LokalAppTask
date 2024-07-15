package com.example.naukri.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyJob2::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun jobDao(): JobDao

}