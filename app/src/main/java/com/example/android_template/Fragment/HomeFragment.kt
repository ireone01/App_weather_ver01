package com.example.android_template.Fragment

import android.os.Bundle
import android.util.Log
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
import com.example.android_template.Data.fetchForecastDay
import com.example.android_template.Data.fetchForecastHour
import com.example.android_template.Data.fetchHourlyFragment
import com.example.android_template.Data.fetchWeatherData
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
                mList.add(Data.HourlyFragmentData(it))
            }
            currentCondition.await().let {
                mList.add(Data.CurrentConditionData(it))
            }
            sunMoon.await().let {
                mList.add(Data.SunMoonData(it))
            }
            forecastHour.await().let {
                mList.add(Data.ForecastHourData(it))
            }
            forecastDay.await().let {
                mList.add(Data.ForecastDayData(it))
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
