package com.example.android_template.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api
import com.example.android_template.Adapter.DailyAdapter
import com.example.android_template.Data.DailyFragmentItem
import com.example.android_template.Data.Data
import com.example.android_template.databinding.DailyFragmentBinding
import com.example.android_template.Data.fetDailyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DailyFragment : Fragment() {
    private lateinit var binding : DailyFragmentBinding
    private lateinit var mList: ArrayList<Data>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DailyFragmentBinding.inflate(
          inflater,container,false
      )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mList = ArrayList()


//        prepareData()
//        val adapter = DailyAdapter(mList)
//        binding.mainRecyclerView.adapter = adapter



        CoroutineScope(Dispatchers.Main).launch {
            val dailyfrag = async { fetDailyFragment(Api.apiForecastDay) }

            dailyfrag.await().let {
                mList.add(Data.DailyFragmentData(it))
            }
            val adapter = DailyAdapter(mList)
            binding.mainRecyclerView.adapter = adapter
        }
    }
    private fun prepareData(){
        val forecastday = ArrayList<DailyFragmentItem>()
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","12","45","12"))
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","23","98","34"))
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","34","72","23"))
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","12","32","76"))
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","23","26","45"))
        forecastday.add(DailyFragmentItem("2024-07-25T05:28:00+07:00","19","45","27"))
        mList.add(Data.DailyFragmentData(forecastday))
    }
}