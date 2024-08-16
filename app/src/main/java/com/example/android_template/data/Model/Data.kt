package com.example.Android_weather_app.data.Model

import com.example.Android_weather_app.Current_Condition
import com.example.Android_weather_app.Daily_FragmentItem
import com.example.Android_weather_app.Forecast_Day
import com.example.Android_weather_app.Forecast_Hour
import com.example.Android_weather_app.Hourly_FragmentItem
import com.example.Android_weather_app.Sun_Moon


sealed class Data {
    data class CurrentConditionData(val currentConditionList: List<Current_Condition>) : Data()
    data class SunMoonData(val sunMoonList: List<Sun_Moon>) : Data()
    data class ForecastHourData(val forecastHourList : List<Forecast_Hour>) : Data()
    data class ForecastDayData(val forecastDayList: List<Forecast_Day>) : Data()
    data class HourlyFragmentData(val hourlyFragmentList : List<Hourly_FragmentItem>) : Data()
    data class DailyFragmentData(val dailyFragmentList : List<Daily_FragmentItem>) : Data()
}