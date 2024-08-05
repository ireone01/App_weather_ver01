package com.example.android_template.Screen.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api.Api
import com.example.android_template.data.Model.Data
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

        Api.onLocationKeyUpdated = {
            updateWeatherHome()
        }
        updateWeatherHome()
    }
    fun updateWeatherHome() {
        Log.d("HomeFragment_TT", "updateWeatherHome called")
        CoroutineScope(Dispatchers.Main).launch {
            val currentCondition =async { fetchWeatherData(Api.apiUrl) }
            val sunMoon  = async { fetSunMoon(Api.apiSunMoon) }
            val forecastHour = async { fetchForecastHour(Api.apiForecastHour) }
            val forecastDay = async { fetchForecastDay(Api.apiForecastDay) }
            val temp  = async { fetchHourlyFragment(Api.apiForecastHour) }

            if(::mList.isInitialized) {
                mList.clear()
            }else{
                mList=ArrayList()
            }

            temp.await().let {
                mList.add(com.example.android_template.data.Model.Data.HourlyFragmentData(it))
            }
            currentCondition.await().let {
                mList.add(com.example.android_template.data.Model.Data.CurrentConditionData(it))
            }
            sunMoon.await().let {
                mList.add(com.example.android_template.data.Model.Data.SunMoonData(it))
            }
            forecastHour.await().let {
                mList.add(com.example.android_template.data.Model.Data.ForecastHourData(it))
            }
            forecastDay.await().let {
                mList.add(com.example.android_template.data.Model.Data.ForecastDayData(it))
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
