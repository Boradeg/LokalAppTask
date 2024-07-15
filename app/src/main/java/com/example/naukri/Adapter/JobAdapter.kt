package com.example.naukri.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.naukri.View.DetailedActivity
import com.example.naukri.View.MainActivity
import com.example.naukri.PojoClass.Result
import com.example.naukri.R

class JobAdapter(private var items: List<Result>, private val context: Context, private val mainActivity: MainActivity) : RecyclerView.Adapter<JobAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_job, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        // Handle null or default values for properties
        val place = item?.primary_details?.Place ?: "Unknown"
        val jobCategory = item?.job_category ?: "Unknown"
        val companyName = item?.company_name ?: "Unknown"
        val maxSalary = item?.salary_max ?: 0

        holder.locationTxt.text = place
        holder.titleTxt.text = jobCategory
        holder.companyTxt.text = companyName
        holder.salaryTxt.text = "$maxSalary Rs"

        // Determine which image to display based on position modulo 4
        val imageResource = when (position % 4) {
            0 -> R.drawable.img_1
            1 -> R.drawable.img_2
            2 -> R.drawable.img_3
            3 -> R.drawable.img_4
            else -> R.drawable.img_2 // Handle any unexpected cases
        }
        holder.imageView.setImageResource(imageResource)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailedActivity::class.java)
                .putExtra("company_name", companyName)
                .putExtra("experience", item?.experience?.toString() ?: "Unknown")
                .putExtra("job_hours", item?.job_hours?.toString() ?: "Unknown")
                .putExtra("job_role", item?.job_role?.toString() ?: "Unknown")
                .putExtra("city_location", item?.city_location?.toString() ?: "Unknown")
                .putExtra("salary_max", maxSalary?.toString())
                .putExtra("salary_min", item.salary_min?.toString() ?: "Unknown")
                .putExtra("primary_details.Experience", item.primary_details?.Experience?.toString() ?: "Unknown")
                .putExtra("primary_details.Place", place)
                .putExtra("created_on", item?.created_on?.toString() ?: "06/10/2023")
                .putExtra("job_category", jobCategory)
                .putExtra("job_role", item.job_role ?: "Unknown")
                .putExtra("job_title", item.title ?: "Unknown")

            context.startActivity(intent)
        }

        holder.bookmarkImg.setOnClickListener {
            holder.bookmarkImg.setImageResource(R.drawable.bookmark)
            mainActivity.insertData(item)
        }
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.pic)
        val bookmarkImg: ImageView = view.findViewById(R.id.bookmarkImg)
        val locationTxt: TextView = view.findViewById(R.id.locationTxt)
        val titleTxt: TextView = view.findViewById(R.id.titleTxt)
        val companyTxt: TextView = view.findViewById(R.id.companyTxt)
        val salaryTxt: TextView = view.findViewById(R.id.salaryTxt)
    }
}
