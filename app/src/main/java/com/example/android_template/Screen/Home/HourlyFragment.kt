package com.example.android_template.Screen.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api.Api
import com.example.android_template.AppDatabase

import com.example.android_template.NetworkUtils.isInternetAvailable
import com.example.android_template.data.Model.Data
import com.example.android_template.databinding.HourFragmentBinding
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetchHourlyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HourlyFragment : Fragment(){
    private var _binding : HourFragmentBinding? = null
    private  val binding get() = _binding!!
    private lateinit var mList: ArrayList<Data>
    private lateinit var db: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = HourFragmentBinding.inflate(inflater
            ,container
            ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        db = AppDatabase.getDatabase(requireContext())
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        mList = ArrayList()
        updateHourly()
    }
    fun updateHourly(){
        CoroutineScope(Dispatchers.Main).launch {
            if(isInternetAvailable(requireContext())){
            val hourlyfragment = async { fetchHourlyFragment(Api.apiForecastHour) }
            val result = hourlyfragment.await()

            if(result.isNotEmpty()){
                db.hourlyfragmentDAO().clearAll()
                db.hourlyfragmentDAO().insterAll(result)
                val cachedData = db.hourlyfragmentDAO().getAllConditions()
                mList.clear()
                mList.add(Data.HourlyFragmentData(cachedData))
            }else{
                val cachedData = db.hourlyfragmentDAO().getAllConditions()
                mList.add(Data.HourlyFragmentData(cachedData))
            }

                }else{
                val cachedData = db.hourlyfragmentDAO().getAllConditions()
                mList.add(Data.HourlyFragmentData(cachedData))
            }
            _binding?.let { binding ->
                val adapter = HourlyAdapter(mList)
                binding.mainRecyclerView.adapter = adapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}