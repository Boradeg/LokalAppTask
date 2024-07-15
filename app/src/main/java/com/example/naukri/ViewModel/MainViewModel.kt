package com.example.naukri.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.naukri.Repository.JobRepository
import com.example.naukri.Database.MyJob2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context:Application):AndroidViewModel(context){
    private val jobRepository: JobRepository = JobRepository(context)

    private val _favoriteJobs = MutableLiveData<List<MyJob2>>()
    val favoriteJobs: LiveData<List<MyJob2>> get() = _favoriteJobs

    private var _allJobs = MutableLiveData<List<com.example.naukri.PojoClass.Result>>()
    val allJobs: LiveData<List<com.example.naukri.PojoClass.Result>> get() = _allJobs


    fun getAllFavoriteJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteJobs = jobRepository.getAllFavoriteJobs()
            _favoriteJobs.postValue(favoriteJobs)
        }
    }
    fun getJobsFromApi(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                val allJobs = jobRepository.getJobsFromApi(page)
                _allJobs.postValue(allJobs)
        }
    }
     fun insertDataInRoom(obj : MyJob2){
         viewModelScope.launch {
             jobRepository.insertDataInRoom(obj)
         }
    }
}
