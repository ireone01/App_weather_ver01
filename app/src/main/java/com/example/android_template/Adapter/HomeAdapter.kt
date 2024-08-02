package com.example.android_template.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_template.Data.CurrentCondition
import com.example.android_template.Data.Data
import com.example.android_template.Data.DataType
import com.example.android_template.Data.ForecastDay
import com.example.android_template.Data.ForecastHour
import com.example.android_template.Data.SunMoon
import com.example.android_template.databinding.ACurrentConditionBinding

class HomeAdapter(private val weather : List<Data>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class ACurrentConditionHolder(private val binding : ACurrentConditionBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bindCurrentCondition(recyclerList : List<CurrentCondition>){
                    binding.childRecyclerView.setHasFixedSize(true)
                    binding.childRecyclerView.layoutManager= LinearLayoutManager(binding.root.context,RecyclerView.VERTICAL,false)
                    val adapter = ChildAdapter(DataType.CURRENT_CONDITION, CurrentConditionList = recyclerList)
                    binding.childRecyclerView.adapter =adapter
                    binding.textA.text="Điều Kiện Hiện Tại"
                }
                fun bindSunMoon(sunmoonlist : List<SunMoon>){
                    binding.childRecyclerView.setHasFixedSize(true)
                    binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL,false)
                    val adapter  = ChildAdapter(DataType.SUN_MOON_TYPE, SunMoonList = sunmoonlist)
                    binding.childRecyclerView.adapter =adapter
                    binding.textA.text="Mặt Trời & Mặt Trăng"
                }
                fun bindForecastHour(forecasthourList : List<ForecastHour>){
                    binding.childRecyclerView.setHasFixedSize(true)
                    binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context,RecyclerView.HORIZONTAL,false)
                    val adapter = ChildAdapter(DataType.FORECAST_HOUR, ForecastHourList = forecasthourList)
                    binding.childRecyclerView.adapter = adapter
                    binding.textA.text = "Theo Giờ"
                }
                fun bindForecastDay(forecastDayList: List<ForecastDay>){
                    binding.childRecyclerView.setHasFixedSize(true)
                    binding.childRecyclerView.layoutManager = LinearLayoutManager(binding.root.context,RecyclerView.VERTICAL,false)
                    val adapter = ChildAdapter(DataType.FORECAST_DAY, ForecastDayList = forecastDayList)
                    binding.childRecyclerView.adapter = adapter
                    binding.textA.text = "Theo Ngày"
                }
            }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
               val binding = ACurrentConditionBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               )
        return ACurrentConditionHolder(binding)
    }

    override fun getItemCount(): Int {
        return weather.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(weather[position]){
                    is Data.CurrentConditionData-> DataType.CURRENT_CONDITION
                    is Data.SunMoonData -> DataType.SUN_MOON_TYPE
                    is Data.ForecastHourData -> DataType.FORECAST_HOUR
                    is Data.ForecastDayData -> DataType.FORECAST_DAY
            else -> throw IllegalArgumentException("Invalid")
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = weather[position]
        when (data) {
            is Data.CurrentConditionData -> {
                if (holder is ACurrentConditionHolder) {
                    holder.bindCurrentCondition(data.currentConditionList)
                }
            }
            is Data.SunMoonData -> {
                if (holder is ACurrentConditionHolder) {
                    holder.bindSunMoon(data.sunMoonList)
                }
            }
            is Data.ForecastHourData -> {
                if(holder is ACurrentConditionHolder) {
                    holder.bindForecastHour(data.forecastHourList)
                }
            }
            is Data.ForecastDayData ->{
                if(holder is ACurrentConditionHolder){
                    holder.bindForecastDay(data.forecastDayList)
                }
            }
            else -> throw IllegalArgumentException("Invalid")
        }
    }



}