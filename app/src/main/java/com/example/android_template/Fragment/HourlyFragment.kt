package com.example.android_template.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api
import com.example.android_template.Data.Data
import com.example.android_template.Adapter.HourlyAdapter
import com.example.android_template.Data.HourlyFragmentItem
import com.example.android_template.databinding.HourFragmentBinding
import com.example.android_template.Data.fetchHourlyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HourlyFragment : Fragment(){
    private lateinit var binding: HourFragmentBinding
    private lateinit var mList: ArrayList<Data>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HourFragmentBinding.inflate(inflater
            ,container
            ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mList = ArrayList()
//        prepareData()
//        val adapter = HourlyAdapter(mList)
//        binding.mainRecyclerView.adapter =adapter
             CoroutineScope(Dispatchers.Main).launch {
            val hourlyfragment = async { fetchHourlyFragment(Api.apiForecastHour) }
            hourlyfragment.await().let {
                mList.add(Data.HourlyFragmentData(it))
            }
            val adapter = HourlyAdapter(mList)
            binding.mainRecyclerView.adapter = adapter
        }
    }
    private fun prepareData(){
        val hourly = ArrayList<HourlyFragmentItem>()
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        hourly.add(HourlyFragmentItem("2024-07-25T05:28:00+07:00","23","45","12","2024-07-25T05:28:00+07:00"))
        mList.add(Data.HourlyFragmentData(hourly))

    }
}