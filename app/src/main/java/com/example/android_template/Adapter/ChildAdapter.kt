package com.example.android_template.Adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android_template.Data.CurrentCondition
import com.example.android_template.Data.DailyFragmentItem
import com.example.android_template.Data.DataType
import com.example.android_template.Data.ForecastDay
import com.example.android_template.Data.ForecastHour
import com.example.android_template.Data.HourlyFragmentItem
import com.example.android_template.Data.SunMoon
import com.example.android_template.R
import com.example.android_template.calculateDuration
import com.example.android_template.calculateDuration.Companion.calculateHour
import com.example.android_template.calculateDuration.Companion.extractDay
import com.example.android_template.calculateDuration.Companion.extractTime
import com.example.android_template.calculateDuration.Companion.fahrenheitToCelsius

import com.example.android_template.databinding.CurrentConditionBinding
import com.example.android_template.databinding.DailyFragmentItemBinding
import com.example.android_template.databinding.ForecastDayBinding
import com.example.android_template.databinding.ForecastHourBinding
import com.example.android_template.databinding.FragmentAppBarBinding
import com.example.android_template.databinding.HourFragmentItemBinding
import com.example.android_template.databinding.SunMoonBinding

class ChildAdapter(private val ViewType : Int
                   , private val CurrentConditionList : List<CurrentCondition> = listOf()
                   , private val SunMoonList : List<SunMoon> = listOf()
                   , private val ForecastHourList : List<ForecastHour> = listOf()
                   , private val ForecastDayList : List<ForecastDay> = listOf()
                    , private val HourlyFragmentList : List<HourlyFragmentItem> = listOf()
                    , private val DailyFragmentList : List<DailyFragmentItem> = listOf()
)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    inner class CurrentConditionHolder(private val binding: CurrentConditionBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bindCurrentCondition(CurrentItem: CurrentCondition){
                  binding.textLabel.text = CurrentItem.Label
                    binding.textValue.text = CurrentItem.Value
                    binding.textUnit.text = CurrentItem.Unit
                }
            }
    inner class SunMoonHolder(private val binding: SunMoonBinding):
            RecyclerView.ViewHolder(binding.root){
                @RequiresApi(Build.VERSION_CODES.O)
                fun bindSunMoon(SunMoonItem : SunMoon){
                    val rise = calculateDuration.extractTime(SunMoonItem.Rise)
                    val set = calculateDuration.extractTime(SunMoonItem.Set)
                    if(SunMoonItem.Sun_or_Moon == "Sun"){
                        binding.imgMoon.setImageResource(R.drawable.sun)

                        binding.tvmoon.text = calculateHour("Sun",rise,set)
                        binding.mtv2moon.text = "Mọc : $rise"
                        binding.mtv3moon.text = "Lặn : $set"
                    }else{
                        binding.imgMoon.setImageResource(R.drawable.moon)
                        binding.tvmoon.text = calculateHour("Moon",rise,set)
                        binding.mtv2moon.text = "Mọc : $rise"
                        binding.mtv3moon.text = "Lặn : $set"
                    }


                }
            }
    inner class ForecastHourHolder(private val binding: ForecastHourBinding):
            RecyclerView.ViewHolder(binding.root){
                @RequiresApi(Build.VERSION_CODES.O)
                fun bindForecastHour(ForeHourItem: ForecastHour){

                    when{
                        ForeHourItem.forecast_rain.toInt() <30 -> {
                            binding.forecastImg.setImageResource(R.drawable.sun)
                            binding.forecastHour.text = extractTime( ForeHourItem.forecast_time)
                            binding.forecastT.text = fahrenheitToCelsius(ForeHourItem.forecast_tem.toDouble())+ "°C"
                            binding.forecastRain.text = ForeHourItem.forecast_rain + "%"
                        }
                        ForeHourItem.forecast_rain.toInt() <50 -> {
                            binding.forecastImg.setImageResource(R.drawable.cloud)
                            binding.forecastHour.text = extractTime( ForeHourItem.forecast_time)
                            binding.forecastT.text = fahrenheitToCelsius(ForeHourItem.forecast_tem.toDouble())+ "°C"
                            binding.forecastRain.text = ForeHourItem.forecast_rain + "%"
                        }
                        else -> {
                            binding.forecastImg.setImageResource(R.drawable.rain)
                            binding.forecastHour.text = extractTime(ForeHourItem.forecast_time)
                            binding.forecastT.text = fahrenheitToCelsius(ForeHourItem.forecast_tem.toDouble()) + "°C"
                            binding.forecastRain.text = ForeHourItem.forecast_rain + "%"
                        }
                    }
                }
            }
    inner class ForecastDayHolder(private val binding : ForecastDayBinding):
            RecyclerView.ViewHolder(binding.root){
                @RequiresApi(Build.VERSION_CODES.O)
                fun binForecastDay(ForeDayItem : ForecastDay){

                    when{
                        ForeDayItem.rain.toInt() < 30 ->{
                        binding.imgDay.setImageResource(R.drawable.sun)
                        binding.tvDay.text = extractDay(ForeDayItem.day)
                            binding.tvDay2.text = fahrenheitToCelsius(ForeDayItem.tem_max.toDouble()) + "°C"
                            binding.tvDay3.text = fahrenheitToCelsius(ForeDayItem.tem_min.toDouble()) + "°C"
                        binding.tvDay4.text = ForeDayItem.rain + "%"
                        }
                        (ForeDayItem.rain.toInt() <50) ->{
                            binding.imgDay.setImageResource(R.drawable.cloud)
                            binding.tvDay.text = extractDay(ForeDayItem.day)
                            binding.tvDay2.text = fahrenheitToCelsius(ForeDayItem.tem_max.toDouble()) + "°C"
                            binding.tvDay3.text = fahrenheitToCelsius(ForeDayItem.tem_min.toDouble()) + "°C"
                            binding.tvDay4.text = ForeDayItem.rain + "%"
                        }
                        else -> {
                            binding.imgDay.setImageResource(R.drawable.rain)
                            binding.tvDay.text = extractDay(ForeDayItem.day)
                            binding.tvDay2.text = fahrenheitToCelsius(ForeDayItem.tem_max.toDouble()) + "°C"
                            binding.tvDay3.text = fahrenheitToCelsius(ForeDayItem.tem_min.toDouble()) + "°C"
                            binding.tvDay4.text = ForeDayItem.rain + "%"
                        }
                    }
                }

            }
    inner class HourlyFragmentItemHolder(private val binding : HourFragmentItemBinding):
        RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindHourlyFragmentItem(HourlyFragItem : HourlyFragmentItem){
            when{
                HourlyFragItem.rain.toInt() < 30 ->{
                    binding.imgDay.setImageResource(R.drawable.sun)
                    binding.tvDay.text = extractTime(HourlyFragItem.hour).substring(0,5)
                    binding.tvDay2.text = fahrenheitToCelsius(HourlyFragItem.tem.toDouble()) + "°C"
                    binding.tvDay3.text = fahrenheitToCelsius(HourlyFragItem.rel_tem.toDouble()) + "°C"
                    binding.tvDay4.text = HourlyFragItem.rain + "%"
                }
                (HourlyFragItem.rain.toInt() <50) ->{
                    binding.imgDay.setImageResource(R.drawable.cloud)
                    binding.tvDay.text = extractTime(HourlyFragItem.hour).substring(0,5)
                    binding.tvDay2.text = fahrenheitToCelsius(HourlyFragItem.tem.toDouble()) + "°C"
                    binding.tvDay3.text = fahrenheitToCelsius(HourlyFragItem.rel_tem.toDouble()) + "°C"
                    binding.tvDay4.text = HourlyFragItem.rain + "%"
                }
                else -> {
                    binding.imgDay.setImageResource(R.drawable.rain)
                    binding.tvDay.text = extractTime(HourlyFragItem.hour).substring(0,5)
                    binding.tvDay2.text = fahrenheitToCelsius(HourlyFragItem.tem.toDouble()) + "°C"
                    binding.tvDay3.text = fahrenheitToCelsius(HourlyFragItem.rel_tem.toDouble()) + "°C"
                    binding.tvDay4.text = HourlyFragItem.rain + "%"
                }
            }
        }

    }
    inner class DailyFragmentItemHolder(private val binding : DailyFragmentItemBinding):
        RecyclerView.ViewHolder(binding.root){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindDailyFragmentItem(DailyItem : DailyFragmentItem){
            var maxpar = 300
            var t_par =15
            when{

                DailyItem.rain.toInt() < 30 ->{
                    binding.imgv1.setImageResource(R.drawable.sun)
                    binding.tv1.text = extractDay(DailyItem.day)
                    binding.tv4.text = fahrenheitToCelsius(DailyItem.tem_max.toDouble()) + "°C"
                    binding.tv2.text = fahrenheitToCelsius(DailyItem.tem_min.toDouble()) + "°C"
                    binding.tv3.text = DailyItem.rain + "%"
                    binding.imgv2.setImageResource(R.drawable.rain_drop)

                    val min = (DailyItem.tem_min.toDouble() / t_par).toDouble()
                    val max = (DailyItem.tem_max.toDouble() / t_par).toDouble()
                    val par = binding.view1.layoutParams
                    par.height = (maxpar*(max - min)).toInt()
                    binding.view1.layoutParams = par

                }
                (DailyItem.rain.toInt() <50) ->{
                    binding.imgv1.setImageResource(R.drawable.cloud)
                    binding.tv1.text = extractDay(DailyItem.day)
                    binding.tv4.text = fahrenheitToCelsius(DailyItem.tem_max.toDouble()) + "°C"
                    binding.tv2.text = fahrenheitToCelsius(DailyItem.tem_min.toDouble()) + "°C"
                    binding.tv3.text = DailyItem.rain + "%"
                    binding.imgv2.setImageResource(R.drawable.rain_drop)

                    val min = (DailyItem.tem_min.toDouble() / t_par).toDouble()
                    val max = (DailyItem.tem_max.toDouble() / t_par).toDouble()
                    val par = binding.view1.layoutParams
                    par.height = (maxpar*(max - min)).toInt()
                    binding.view1.layoutParams = par
                }
                else -> {
                    binding.imgv1.setImageResource(R.drawable.rain)
                    binding.tv1.text = extractDay(DailyItem.day)
                    binding.tv4.text = fahrenheitToCelsius(DailyItem.tem_max.toDouble()) + "°C"
                    binding.tv2.text = fahrenheitToCelsius(DailyItem.tem_min.toDouble()) + "°C"
                    binding.tv3.text = DailyItem.rain + "%"
                    binding.imgv2.setImageResource(R.drawable.rain_drop)

                    val min = (DailyItem.tem_min.toDouble() / t_par).toDouble()
                    val max = (DailyItem.tem_max.toDouble() / t_par).toDouble()
                    val par = binding.view1.layoutParams
                    par.height = (maxpar*(max - min)).toInt()
                    binding.view1.layoutParams = par
                }
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return   when(viewType) {
           DataType.CURRENT_CONDITION -> {
               val binding = CurrentConditionBinding.inflate(
                   LayoutInflater.from(parent.context),
                   parent,
                   false
               )
               return CurrentConditionHolder(binding)
           }
           DataType.SUN_MOON_TYPE -> {
                      val binding = SunMoonBinding.inflate(
                      LayoutInflater.from(parent.context),
                      parent,
                      false
                  )
                  return SunMoonHolder(binding)
           }
            DataType.FORECAST_HOUR ->{
                        val binding = ForecastHourBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent, false
                        )
                return ForecastHourHolder(binding)
            }
            DataType.FORECAST_DAY ->{
                        val binding = ForecastDayBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,false
                        )
                return ForecastDayHolder(binding)
            }
            DataType.HOURLY_FRAGMENT ->{
                val binding = HourFragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,false
                )
                return HourlyFragmentItemHolder(binding)
            }
            DataType.DAILY_FRAGMENT -> {
                val binding = DailyFragmentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DailyFragmentItemHolder(binding)
            }

            else ->throw IllegalArgumentException("Invalid view type")
       }
    }

    override fun getItemCount(): Int {
      return when(ViewType){
          DataType.CURRENT_CONDITION ->{
              CurrentConditionList.size
          }
          DataType.SUN_MOON_TYPE ->{
              SunMoonList.size
          }
          DataType.FORECAST_HOUR ->{
              ForecastHourList.size
          }
          DataType.FORECAST_DAY ->{
              ForecastDayList.size
          }
          DataType.HOURLY_FRAGMENT ->{
              HourlyFragmentList.size
          }
          DataType.DAILY_FRAGMENT ->{
              DailyFragmentList.size
          }
          else -> throw IllegalArgumentException("Invalid view type")
      }
    }

    override fun getItemViewType(position: Int): Int {
        return ViewType
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CurrentConditionHolder ->{
                holder.bindCurrentCondition(CurrentConditionList[position])
            }
            is SunMoonHolder ->{
                holder.bindSunMoon(SunMoonList[position])
            }
            is ForecastHourHolder ->{
                holder.bindForecastHour(ForecastHourList[position])
            }
            is ForecastDayHolder ->{
                holder.binForecastDay(ForecastDayList[position])
            }
            is HourlyFragmentItemHolder ->{
                holder.bindHourlyFragmentItem(HourlyFragmentList[position])
            }
            is DailyFragmentItemHolder ->{
                holder.bindDailyFragmentItem(DailyFragmentList[position])
            }
        }

    }


}

