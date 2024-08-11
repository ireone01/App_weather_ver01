package com.example.android_template.data.Model


sealed class Data {
    data class CurrentConditionData(val currentConditionList: List<CurrentCondition>) : Data()
    data class SunMoonData(val sunMoonList: List<SunMoon>) : Data()
    data class ForecastHourData(val forecastHourList : List<ForecastHour>) : Data()
    data class ForecastDayData(val forecastDayList: List<ForecastDay>) : Data()
    data class HourlyFragmentData(val hourlyFragmentList : List<HourlyFragmentItem>) : Data()
    data class DailyFragmentData(val dailyFragmentList : List<DailyFragmentItem>) : Data()
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
data class HourlyFragmentItem(
    val hour : String,
    val tem : String ,
    val rel_tem : String ,
    val rain : String,
    val day : String
)
data class DailyFragmentItem(
    val day : String,
    val tem_min : String ,
    val tem_max : String ,
    val rain : String
)