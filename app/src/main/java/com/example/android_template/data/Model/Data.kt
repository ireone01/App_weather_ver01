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
// SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy

// location key
//http://dataservice.accuweather.com/locations/v1/cities/search?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy&q=Hanoi
//353412

//  dự báo hiện ta
//http://dataservice.accuweather.com/currentconditions/v1/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy


// dự báo 5 ngày
// http://dataservice.accuweather.com/forecasts/v1/daily/5day/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy

// Dự báo hàng giờ
// http://dataservice.accuweather.com/forecasts/v1/hourly/12hour/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy

// Cảnh báo thời tiết
//http://dataservice.accuweather.com/alerts/v1/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy

//Chỉ số chất lượng không khí
//http://dataservice.accuweather.com/currentconditions/v1/261208/historical/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy

//Radar
//http://dataservice.accuweather.com/radar/v1/location_key?apikey=SC8yMrpOtTb4IJA2MFxXHzlvrVMAxcNy