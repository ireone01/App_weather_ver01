package com.example.android_template.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_template.Data.Data
import com.example.android_template.Data.DataType
import com.example.android_template.Data.HourlyFragmentItem
import com.example.android_template.calculateDuration.Companion.extractDay
import com.example.android_template.databinding.ACurrentConditionBinding

class HourlyAdapter(private val weather : List<Data>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>(){
            inner class ACurrentConditionHolder(private val binding : ACurrentConditionBinding):
                    RecyclerView.ViewHolder(binding.root){

                        @RequiresApi(Build.VERSION_CODES.O)
                        fun bindHourlyFragment(hourlyList : List<HourlyFragmentItem>){
                            if (hourlyList.isNotEmpty()) {
                                binding.childRecyclerView.setHasFixedSize(true)
                                binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
                                val adapter = ChildAdapter(DataType.HOURLY_FRAGMENT, HourlyFragmentList = hourlyList)
                                binding.childRecyclerView.adapter = adapter
                                binding.textA.text = extractDay(hourlyList[0].day)
                            } else {

                                binding.textA.text = "Không có dữ liệu nhận về từ API"
                            }
                        }
                    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val binding = ACurrentConditionBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,false
       )
        return ACurrentConditionHolder(binding)
    }

    override fun getItemCount(): Int {
      return weather.size
    }

    override fun getItemViewType(position: Int): Int {
        return DataType.HOURLY_FRAGMENT
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = weather[position]
        when(data) {
            is Data.HourlyFragmentData -> {
                if(holder is ACurrentConditionHolder){
                    holder.bindHourlyFragment(data.hourlyFragmentList)
                }
            }
            else -> throw IllegalArgumentException("Invalid data type at position $position: ${data::class.java.simpleName}")

        }
    }


}