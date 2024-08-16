package com.example.Android_weather_app.Screen.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Android_weather_app.Api.Api
import com.example.Android_weather_app.AppDatabase
import com.example.Android_weather_app.NetworkUtils.isInternetAvailable
import com.example.Android_weather_app.data.Model.Data
import com.example.Android_weather_app.databinding.DailyFragmentBinding
import com.example.Android_weather_app.data.Respository.Source.Remote.FetchJson.fetDailyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DailyFragment : Fragment() {
    private var _binding: DailyFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mList: ArrayList<Data>
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DailyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        mList = ArrayList()
        updateDaily()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


     fun updateDaily() {
        CoroutineScope(Dispatchers.Main).launch {
            if(isInternetAvailable(requireContext())){
            val dailyfrag = async { fetDailyFragment(Api.apiForecastDay) }

            val daily_result = dailyfrag.await()
            if(daily_result.isNotEmpty()){
                db.dailyfragmentDAO().clearAll()
                db.dailyfragmentDAO().insterAll(daily_result)
                val catchedData = db.dailyfragmentDAO().getAllConditions()
                mList.clear()
                mList.add(Data.DailyFragmentData(catchedData))
            }else {
                val catchedData = db.dailyfragmentDAO().getAllConditions()
                mList.add(Data.DailyFragmentData(catchedData))
            }

            }else{
                    val catchedData = db.dailyfragmentDAO().getAllConditions()
                    mList.add(Data.DailyFragmentData(catchedData))
                }


            _binding?.let { binding ->
                val adapter = DailyAdapter(mList)
                binding.mainRecyclerView.adapter = adapter
            }
        }
    }
}
