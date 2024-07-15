package com.example.naukri.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.naukri.Adapter.FavoriteJobAdapter
import com.example.naukri.Adapter.JobAdapter
import com.example.naukri.Database.AppDatabase
import com.example.naukri.Database.JobDao
import com.example.naukri.Database.MyJob2
import com.example.naukri.PojoClass.Result
import com.example.naukri.R
import com.example.naukri.Repository.JobRepository
import com.example.naukri.ViewModel.MainViewModel
import com.example.naukri.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var adapter: JobAdapter? = null
    private var adapter2: FavoriteJobAdapter? = null

    private var list: ArrayList<Result> = ArrayList()
    private var favoriteList: ArrayList<MyJob2> = ArrayList()

    private var currentPage = 1
    private var isLoading = false
    private lateinit var userDao: JobDao
    private lateinit var db: AppDatabase
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jobRepository: JobRepository = JobRepository(this)
        userDao = jobRepository.userDao
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        setDrawable()
        initLocation()
        setBadge()
        setRecyclerView()

        // Load initial data
        viewModel.getJobsFromApi(1)
        viewModel.getAllFavoriteJobs()
        //observeData
        viewModel.allJobs.observe(this) { newList ->

            list.addAll(newList)
            adapter?.notifyDataSetChanged()
            //
            isLoading = false
            binding.progressBar.visibility = View.GONE
        }
        viewModel.favoriteJobs.observe(this) {
            favoriteList.clear()
            favoriteList.addAll(it)
            adapter2?.notifyDataSetChanged()

        }

        //set bottomNavBar
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_favorite -> {
                    binding.progressBar.visibility = View.VISIBLE
                    adapter2 = FavoriteJobAdapter(favoriteList, this, this)
                    binding.recyclerView.adapter = adapter2
                    viewModel.getAllFavoriteJobs()
                }

                R.id.menu_home -> {
                    binding.progressBar.visibility = View.VISIBLE
                    list.clear()
                    currentPage = 1
                    isLoading = false
                    // Reinitialize the adapter
                    adapter = JobAdapter(list, this, this)
                    binding.recyclerView.adapter = adapter
                    // Fetch the initial data again
                    viewModel.getJobsFromApi(currentPage)
                }
            }
            true
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                    loadNextPage()
                }
            }
        })
    }

    private fun setRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = JobAdapter(list, this, this)
        binding.recyclerView.adapter = adapter
    }
    private fun setBadge() {
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.menu_favorite)
        badge.isVisible = true
        badge.number = 5
    }
    fun initLocation() {

        val location = listOf("Pune, Maharashtra", "Benglore,Karnataka", "Mumbai,Maharashtra")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, location)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.locationSpin.adapter = adapter
    }

    fun insertData(obj: Result) {
        val jobObject = MyJob2(
            0,
            obj.job_role,
            obj.primary_details?.Place ?: "",
            obj.company_name,
            obj.salary_max.toString(),
            obj.salary_min.toString(),
            obj.salary_min.toString(),
            obj.job_category,
            obj.created_on.toString(),
            obj.experience.toString(),
            obj.id.toString()
        )
        viewModel.insertDataInRoom(jobObject)
    }

    private fun setDrawable() {
        val drawable = ContextCompat.getDrawable(this, R.drawable.search_icon)
        drawable?.setBounds(0, 0, 50, 50)
        findViewById<EditText>(R.id.editTextText).setCompoundDrawables(
            drawable, null, null, null
        )
    }

    private fun loadJobs(page: Int) {
        isLoading = true
        viewModel.getJobsFromApi(page)
    }

    private fun loadNextPage() {
        isLoading = true
        currentPage++
        loadJobs(currentPage)
    }

}








