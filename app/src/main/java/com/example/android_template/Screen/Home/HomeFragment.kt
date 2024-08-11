package com.example.android_template.Screen.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api.Api
import com.example.android_template.data.Model.CurrentCondition
import com.example.android_template.data.Model.Data
import com.example.android_template.data.Model.ForecastDay
import com.example.android_template.data.Model.ForecastHour
import com.example.android_template.data.Model.SunMoon
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetSunMoon
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetchForecastDay
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetchForecastHour
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetchHourlyFragment
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetchWeatherData
import com.example.android_template.databinding.HomeFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private  val binding get() = _binding!!
    private lateinit var mList: ArrayList<Data>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)


        mList = ArrayList()
        prepareData()
        val adapter = HomeAdapter(mList)
        binding.mainRecyclerView.adapter = adapter
//        Api.onLocationKeyUpdated = {
//            updateWeatherHome()
//        }
//        updateWeatherHome()
//    }
//    fun updateWeatherHome() {
//        Log.d("HomeFragment_TT", "updateWeatherHome called")
//        CoroutineScope(Dispatchers.Main).launch {
//            val currentCondition =async { fetchWeatherData(Api.apiUrl) }
//            val sunMoon  = async { fetSunMoon(Api.apiSunMoon) }
//            val forecastHour = async { fetchForecastHour(Api.apiForecastHour) }
//            val forecastDay = async { fetchForecastDay(Api.apiForecastDay) }
//            val temp  = async { fetchHourlyFragment(Api.apiForecastHour) }
//
//            if(::mList.isInitialized) {
//                mList.clear()
//            }else{
//                mList=ArrayList()
//            }
//
//            temp.await().let {
//                mList.add(com.example.android_template.data.Model.Data.HourlyFragmentData(it))
//            }
//            currentCondition.await().let {
//                mList.add(com.example.android_template.data.Model.Data.CurrentConditionData(it))
//            }
//            sunMoon.await().let {
//                mList.add(com.example.android_template.data.Model.Data.SunMoonData(it))
//            }
//            forecastHour.await().let {
//                mList.add(com.example.android_template.data.Model.Data.ForecastHourData(it))
//            }
//            forecastDay.await().let {
//                mList.add(com.example.android_template.data.Model.Data.ForecastDayData(it))
//            }
//            _binding?.let { binding ->
//                val adapter = HomeAdapter(mList)
//                binding.mainRecyclerView.adapter = adapter
//            }
//        }
//    }
    }
    private fun prepareData() {
        val currentcondition = ArrayList<CurrentCondition>()
        val sunmoon = ArrayList<SunMoon>()
        val forecasthour = ArrayList<ForecastHour>()
        val forecastday = ArrayList<ForecastDay>()

        currentcondition.add(CurrentCondition("Temperature", "28.9", "C"))
        currentcondition.add(CurrentCondition("RealFeel", "33.6", "C"))
        currentcondition.add(CurrentCondition("Wind", "12.8", "km/h"))
        currentcondition.add(CurrentCondition("Wind Gust", "21.4", "km/h"))
        currentcondition.add(CurrentCondition("Humidity", "80", "%"))
        currentcondition.add(CurrentCondition("Indoor Humidity", "80", "%"))


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


        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","12"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","45"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","45"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","56"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","56"))
        forecasthour.add(ForecastHour("2024-07-25T21:45:00+07:00","23","12"))


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

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}
