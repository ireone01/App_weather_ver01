package com.example.android_template.data.Model

import com.example.android_template.Current_Condition
import com.example.android_template.Daily_FragmentItem
import com.example.android_template.Forecast_Day
import com.example.android_template.Forecast_Hour
import com.example.android_template.Hourly_FragmentItem
import com.example.android_template.Sun_Moon


sealed class Data {
    data class CurrentConditionData(val currentConditionList: List<Current_Condition>) : Data()
    data class SunMoonData(val sunMoonList: List<Sun_Moon>) : Data()
    data class ForecastHourData(val forecastHourList : List<Forecast_Hour>) : Data()
    data class ForecastDayData(val forecastDayList: List<Forecast_Day>) : Data()
    data class HourlyFragmentData(val hourlyFragmentList : List<Hourly_FragmentItem>) : Data()
    data class DailyFragmentData(val dailyFragmentList : List<Daily_FragmentItem>) : Data()
}
data class CurrentCondition(
    val Label: String,
    val Value: String,
    val Unit: String
)

data class SunMoon(
    val Sun_or_Moon : String,
    val Rise: String,
    val Set: String,

)
data class ForecastHour(
    val forecast_time : String,
    val forecast_tem : String ,
    val forecast_rain : String
)

data class ForecastDay(
    val day : String,
    val tem_min : String ,
    val tem_max : String ,
    val rain : String
)
//data class HourlyFragmentItem(
//    val hour : String,
//    val tem : String ,
//    val rel_tem : String ,
//    val rain : String,
//    val day : String
//)
data class DailyFragmentItem(
    val day : String,
    val tem_min : String ,
    val tem_max : String ,
    val rain : String
)