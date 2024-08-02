package com.example.android_template.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.*
import com.example.android_template.Adapter.HomeAdapter
import com.example.android_template.Data.CurrentCondition
import com.example.android_template.Data.Data
import com.example.android_template.Data.ForecastDay
import com.example.android_template.Data.ForecastHour
import com.example.android_template.Data.SunMoon
import com.example.android_template.Data.fetSunMoon
//import com.example.android_template.Data.fetchForecastDay
import com.example.android_template.Data.fetchForecastHour
import com.example.android_template.Data.fetchWeatherData
import com.example.android_template.databinding.HomeFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var mList: ArrayList<Data>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mList = ArrayList()

//        prepareData()
//        val adapter = HomeAdapter(mList)
//        binding.mainRecyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            val currentCondition =async { fetchWeatherData(Api.apiUrl) }
            val sunMoon  = async { fetSunMoon(Api.apiSunMoon) }
            val forecastHour = async { fetchForecastHour(Api.apiForecastHour) }
//            val forecastDay = async { fetchForecastDay(Api.apiForecastHour) }
            currentCondition.await().let {
                mList.add(Data.CurrentConditionData(it))
            }
            sunMoon.await().let {
                mList.add(Data.SunMoonData(it))
            }
            forecastHour.await().let {
                mList.add(Data.ForecastHourData(it))
            }
//            forecastDay.await().let {
//                mList.add(Data.ForecastDayData(it))
//            }
            val adapter = HomeAdapter(mList)
            binding.mainRecyclerView.adapter = adapter
        }
    }

    private fun prepareData() {
        val currentcondition = ArrayList<CurrentCondition>()
        currentcondition.add(CurrentCondition("Temperature", "28.9", "C"))
        currentcondition.add(CurrentCondition("RealFeel", "33.6", "C"))
        currentcondition.add(CurrentCondition("Wind", "12.8", "km/h"))
        currentcondition.add(CurrentCondition("Wind Gust", "21.4", "km/h"))
        currentcondition.add(CurrentCondition("Humidity", "80", "%"))
        currentcondition.add(CurrentCondition("Indoor Humidity", "80", "%"))

        val sunmoon = ArrayList<SunMoon>()
        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))
        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))

        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))

        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))

        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))

        sunmoon.add(SunMoon("Sun", "2024-07-25T05:28:00+07:00", "2024-07-25T18:39:00+07:00"))
        sunmoon.add(SunMoon("moon", "2024-07-25T21:45:00+07:00", "2024-07-26T10:11:00+07:00"))

        val forecasthour = ArrayList<ForecastHour>()
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","12"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","45"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","45"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","56"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","56"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","12"))

        val forecastday = ArrayList<ForecastDay>()
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","12"))
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","43"))
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","56"))
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","56"))
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","12"))
        forecastday.add(ForecastDay("2024-07-25T05:28:00+07:00","23","45","34"))
        mList.add(Data.CurrentConditionData(currentcondition))
        mList.add(Data.SunMoonData(sunmoon))
        mList.add(Data.ForecastHourData(forecasthour))
        mList.add(Data.ForecastDayData(forecastday))
    }


}
