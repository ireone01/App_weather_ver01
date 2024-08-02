package com.example.android_template.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_template.Data.DailyFragmentItem
import com.example.android_template.Data.Data
import com.example.android_template.Data.DataType
import com.example.android_template.calculateDuration.Companion.extractDay
import com.example.android_template.databinding.ACurrentConditionBinding

class DailyAdapter(private val weather : List<Data>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
            inner class ACurrentConditionHolder(private val binding: ACurrentConditionBinding):
                    RecyclerView.ViewHolder(binding.root) {

                @RequiresApi(Build.VERSION_CODES.O)
                fun bindDailyFragment(dailyList: List<DailyFragmentItem>) {
                    binding.childRecyclerView.setHasFixedSize(true)
                    binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL,false)
                    val adapter = ChildAdapter(DataType.DAILY_FRAGMENT, DailyFragmentList = dailyList)
                    binding.childRecyclerView.adapter =adapter
                    binding.textA.text = "Tháng "+ extractDay(dailyList[0].day).substring(0,2)
                }
            }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = ACurrentConditionBinding.inflate(
           LayoutInflater.from(parent.context)
           ,parent,false
       )
        return ACurrentConditionHolder(binding)
    }

    override fun getItemCount(): Int {
       return weather.size
    }

    override fun getItemViewType(position: Int): Int {
        return DataType.DAILY_FRAGMENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val data = weather[position]
        when(data) {
            is Data.DailyFragmentData ->{
                if(holder is ACurrentConditionHolder){
                    holder.bindDailyFragment(data.dailyFragmentList)
                }
            }
            else -> throw IllegalArgumentException("Invalid")
        }
    }
}