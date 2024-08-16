package com.example.Android_weather_app.Screen.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.Android_weather_app.R
import com.example.Android_weather_app.Screen.Home.DailyFragment
import com.example.Android_weather_app.Screen.Home.HomeFragment
import com.example.Android_weather_app.Screen.Home.HourlyFragment
import com.example.Android_weather_app.Screen.Home.RadaFragment
import com.example.Android_weather_app.databinding.FragmentNavigationBinding

class NavigationFragment : Fragment() {
    private lateinit var binding: FragmentNavigationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToday.setOnClickListener {
            navigateToFragment(HomeFragment())
        }
        binding.btnHourly.setOnClickListener {
            navigateToFragment(HourlyFragment())
        }
        binding.btnDaily.setOnClickListener {
            navigateToFragment(DailyFragment())
        }
        binding.btnRadar.setOnClickListener {
            navigateToFragment(RadaFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        parentFragmentManager.beginTransaction()
            .replace(R.id.navigation, NavigationFragment())
            .addToBackStack(null)
            .commit()
    }
}
