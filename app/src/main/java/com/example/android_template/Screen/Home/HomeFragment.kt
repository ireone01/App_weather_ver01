package com.example.Android_weather_app.Screen.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Android_weather_app.Api.Api
import com.example.Android_weather_app.AppDatabase
import com.example.Android_weather_app.NetworkUtils.isInternetAvailable
import com.example.Android_weather_app.data.Model.Data
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetSunMoon
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetchForecastDay
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetchForecastHour
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetchHourlyFragment
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetchWeatherData
import com.example.Android_weather_app.databinding.HomeFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private  val binding get() = _binding!!
    private lateinit var mList: ArrayList<Data>
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)


        mList = ArrayList()
        Api.onLocationKeyUpdated = {
            updateWeatherHome()
        }
        updateWeatherHome()
    }
    fun updateWeatherHome() {
        Log.d("HomeFragment_TT", "updateWeatherHome called")
        CoroutineScope(Dispatchers.Main).launch {
            if(isInternetAvailable(requireContext())){
            val currentCondition =async { fetchWeatherData(Api.apiUrl) }
            val sunMoon  = async { fetSunMoon(Api.apiSunMoon) }
            val forecastHour = async { fetchForecastHour(Api.apiForecastHour) }
            val forecastDay = async { fetchForecastDay(Api.apiForecastDay) }
            val temp  = async { fetchHourlyFragment(Api.apiForecastHour) }
            val temp_result = temp.await()
            val curren_result = currentCondition.await()
            val sunmoon_result = sunMoon.await()
            val forehour_result = forecastHour.await()
            val foreday_result =  forecastDay.await()
            if(temp_result.isNotEmpty() && curren_result.isNotEmpty() && sunmoon_result.isNotEmpty()
                && foreday_result.isNotEmpty() && forehour_result.isNotEmpty()){
                db.hourlyfragmentDAO().clearAll()
                db.hourlyfragmentDAO().insterAll(temp_result)
                val hourly = db.hourlyfragmentDAO().getAllConditions()

                db.currentDAO().clearAll()
                db.currentDAO().insertAll(curren_result)
                val curren  = db.currentDAO().getAllConditions()

                db.sunmoonDAO().clearAll()
                db.sunmoonDAO().insterAll(sunmoon_result)
                val sunmon = db.sunmoonDAO().getAllConditions()

                db.forecasthourDAO().clearAll()
                db.forecasthourDAO().insterAll(forehour_result)
                val forehour = db.forecasthourDAO().getAllConditions()

                db.forecastdayDAO().clearAll()
                db.forecastdayDAO().insterAll(foreday_result)
                val foreday = db.forecastdayDAO().getAllConditions()

                mList.clear()
                mList.add(Data.HourlyFragmentData(hourly))
                mList.add(Data.CurrentConditionData(curren))
                mList.add(Data.SunMoonData(sunmon))
                mList.add(Data.ForecastHourData(forehour))
                mList.add(Data.ForecastDayData(foreday))

            }else{
                val hourly = db.hourlyfragmentDAO().getAllConditions()
                val curren  = db.currentDAO().getAllConditions()
                val sunmon = db.sunmoonDAO().getAllConditions()
                val forehour = db.forecasthourDAO().getAllConditions()
                val foreday = db.forecastdayDAO().getAllConditions()
                mList.add(Data.HourlyFragmentData(hourly))
                mList.add(Data.CurrentConditionData(curren))
                mList.add(Data.SunMoonData(sunmon))
                mList.add(Data.ForecastHourData(forehour))
                mList.add(Data.ForecastDayData(foreday))
            }

            }else{
                val hourly = db.hourlyfragmentDAO().getAllConditions()
                val curren  = db.currentDAO().getAllConditions()
                val sunmon = db.sunmoonDAO().getAllConditions()
                val forehour = db.forecasthourDAO().getAllConditions()
                val foreday = db.forecastdayDAO().getAllConditions()
                mList.add(Data.HourlyFragmentData(hourly))
                mList.add(Data.CurrentConditionData(curren))
                mList.add(Data.SunMoonData(sunmon))
                mList.add(Data.ForecastHourData(forehour))
                mList.add(Data.ForecastDayData(foreday))
            }
            _binding?.let { binding ->
                val adapter = HomeAdapter(mList)
                binding.mainRecyclerView.adapter = adapter
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }

