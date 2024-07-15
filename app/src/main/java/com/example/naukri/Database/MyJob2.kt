package com.example.naukri.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="MyJob2")
data class MyJob2(
    @PrimaryKey(autoGenerate=true)
    var id:Int,
    var jobTitle:String,
    var jobPlace:String,
    var companyName:String,
    var totalSalary:String,
    var minSalary:String,
    var maxSalary:String,
    var jobType:String,
    var date:String,
    var experience:String,
    var jobUniqueId:String
)