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
        val place = item.primary_details?.Place ?: "Mumbai"
            holder.locationTxt.text = place
        holder.titleTxt.text=item.job_category
        holder.companyTxt.text=item.company_name
        holder.salaryTxt.text=item.salary_max.toString()+" Rs"
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

            var intent=Intent(context, DetailedActivity::class.java)
                .putExtra("company_name", item.company_name)
            .putExtra("experience", item.experience.toString())
            .putExtra("job_hours", item.job_hours.toString())
            .putExtra("job_role", item.job_role.toString())
            .putExtra("city_location", item.city_location.toString())
            .putExtra("salary_max", item.salary_max.toString())
            .putExtra("salary_min", item.salary_min.toString())
            .putExtra("primary_details.Experience", item.primary_details.Experience.toString())
            .putExtra("primary_details.Place", item.primary_details.Place.toString())
            .putExtra("created_on", item.created_on.toString())
            .putExtra("job_category", item.job_category)
            .putExtra("job_role", item.job_role)
            .putExtra("job_title", item.title)


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
