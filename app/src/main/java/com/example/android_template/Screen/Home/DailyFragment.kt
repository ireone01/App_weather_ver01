package com.example.android_template.Screen.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_template.Api.Api
import com.example.android_template.data.Model.Data
import com.example.android_template.databinding.DailyFragmentBinding
import com.example.android_template.data.Respository.Source.Remote.FetchJson.fetDailyFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DailyFragment : Fragment() {
    private var _binding: DailyFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mList: ArrayList<Data>

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
            val dailyfrag = async { fetDailyFragment(Api.apiForecastDay) }

            if (::mList.isInitialized) {
                mList.clear()
            } else {
                mList = ArrayList()
            }

            dailyfrag.await()?.let {
                mList.add(com.example.android_template.data.Model.Data.DailyFragmentData(it))
            }


            _binding?.let { binding ->
                val adapter = DailyAdapter(mList)
                binding.mainRecyclerView.adapter = adapter
            }
        }
    }
}
