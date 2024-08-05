package com.example.android_template

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_template.Screen.Home.HomeFragment
import com.example.android_template.Screen.Search.NavigationFragment
import com.example.android_template.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.navigation, NavigationFragment())
            .commit()
    }

}
