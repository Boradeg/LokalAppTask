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
import com.example.naukri.Database.MyJob2
import com.example.naukri.R

class FavoriteJobAdapter(private val items: List<MyJob2>, private val context: Context, private val mainActivity: MainActivity)
    : RecyclerView.Adapter<FavoriteJobAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_job, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.locationTxt.text = item.jobPlace
        holder.titleTxt.text=item.jobType
        holder.companyTxt.text=item.companyName
        holder.salaryTxt.text=item.totalSalary.toString()+" Rs"
        // Determine which image to display based on position modulo 4
        val imageResource = when (position % 4) {
            0 -> R.drawable.img_1
            1 -> R.drawable.img_2
            2 -> R.drawable.img_3
            3 -> R.drawable.img_4
            else -> R.drawable.img_2 // Handle any unexpected cases
        }
        holder.bookmarkImg.visibility= View.GONE
        holder.imageView.setImageResource(imageResource)
        holder.itemView.setOnClickListener {

            var intent= Intent(context, DetailedActivity::class.java)
                .putExtra("company_name", item.companyName.toString())
                .putExtra("experience", item.experience.toString())
                .putExtra("salary_max", item.maxSalary.toString())
                .putExtra("salary_min", item.minSalary.toString())
                .putExtra("primary_details.Experience", item.experience.toString())
                .putExtra("primary_details.Place", item.jobPlace.toString())
                .putExtra("created_on", item.date.toString())
                .putExtra("job_category", item.jobType)
                .putExtra("job_role", item.jobTitle)
                .putExtra("job_title", item.jobTitle)

            context.startActivity(intent)
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