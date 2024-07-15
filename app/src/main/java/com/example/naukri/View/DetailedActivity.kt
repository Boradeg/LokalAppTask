package com.example.naukri.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.naukri.databinding.ActivityDetailedBinding

class DetailedActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailedBinding
    private var job_title =""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve extras from the intent
        val companyName = intent.getStringExtra("company_name")
        val experience = intent.getStringExtra("experience")
        val jobRole = intent.getStringExtra("job_role")
        val salaryMax = intent.getStringExtra("salary_max")
        val salaryMin = intent.getStringExtra("salary_min")
        val primaryPlace = intent.getStringExtra("primary_details.Place")
        val createdOn = intent.getStringExtra("created_on")?:"06/10/1999/1"
        val job_category = intent.getStringExtra("job_category")
         job_title = intent.getStringExtra("job_title").toString()

        binding.titleTxt.text=companyName
        binding.locationTxt.text=primaryPlace
        binding.salaryTxt.text="$salaryMin - $salaryMax"
        binding.jobTypeTxt.text=job_category
        binding.dateTxt.text=createdOn?.substring(0,10) ?: "06/10/1999"
        binding.jobTxt.text="Role - $jobRole"
        binding.levelTxt.text="Experience - $experience"
        setupViewPager()
    }
    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Create bundles with data
        val bundle1 = Bundle()
        bundle1.putString("description", job_title)


        // Create instances of fragments
        val tab1 = AboutFragment().apply {
            arguments = bundle1
        }
        val tab2 = CompanyDetailFragment()
        val tab3 = ReviewFragment()

        // Add fragments to adapter
        adapter.addFrag(tab1, "About")
        adapter.addFrag(tab2, "Company")
        adapter.addFrag(tab3, "Review")

        // Set up ViewPager and TabLayout
        binding.viewpager.adapter = adapter
        binding.tabsLayout.setupWithViewPager(binding.viewpager)
    }

    private class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragmentList = arrayListOf<Fragment>()
        private val fragmentTitleList = arrayListOf<String>()

        override fun getCount(): Int = fragmentList.size

        override fun getItem(position: Int): Fragment = fragmentList[position]

        fun addFrag(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence = fragmentTitleList[position]
    }

    fun backButtonListner(view: View) {finish()}
}