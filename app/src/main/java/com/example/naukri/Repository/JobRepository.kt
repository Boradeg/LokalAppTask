package com.example.naukri.Repository

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.example.naukri.Database.AppDatabase
import com.example.naukri.Database.MyJob2
import com.example.naukri.PojoClass.JobModelClass
import com.example.naukri.PojoClass.Result
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JobRepository(private val context: Context) {
    var db = Room.databaseBuilder(context, AppDatabase::class.java, "my_database3")
        .fallbackToDestructiveMigration().build()
    var userDao = db.jobDao()

    suspend fun getJobsFromApi(page: Int): List<Result> {
        return withContext(Dispatchers.IO) {
            try {
                val response = Ion.with(context)
                    .load("https://testapi.getlokalapp.com/common/jobs?page=$page")
                    .`as`(object : TypeToken<JobModelClass>() {})
                    .withResponse()
                    .get()

                response.result?.results ?: emptyList()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to load jobs: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                emptyList<Result>() // Return an empty list on failure
            }
        }
    }
    suspend fun insertDataInRoom(obj: MyJob2) {
        withContext(Dispatchers.IO) {
            try {
                // Check if a record with the same jobUniqueId exists
                val existingJob = userDao.findByJobUniqueId(obj.jobUniqueId)

                if (existingJob != null) {
                    // Job with the same unique ID already exists
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, "This job is already added in favorite", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // Insert the job into the database
                    userDao.insert(obj)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context, "Added in favorite", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Failed to insert job: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun getAllFavoriteJobs(): List<MyJob2> {
        return try {
            userDao.getAllJobs()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to load favorite jobs: ${e.message}", Toast.LENGTH_SHORT).show()
            emptyList() // Return an empty list on failure
        }
    }
}