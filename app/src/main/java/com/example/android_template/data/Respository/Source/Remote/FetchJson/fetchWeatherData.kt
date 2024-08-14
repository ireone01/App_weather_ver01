package com.example.android_template.data.Respository.Source.Remote.FetchJson

import com.example.android_template.Current_Condition
import com.example.android_template.Daily_FragmentItem
import com.example.android_template.Forecast_Day
import com.example.android_template.Forecast_Hour
import com.example.android_template.Hourly_FragmentItem
import com.example.android_template.Sun_Moon
import com.example.android_template.data.Model.CurrentCondition
import com.example.android_template.data.Model.DailyFragmentItem
import com.example.android_template.data.Model.ForecastDay
import com.example.android_template.data.Model.ForecastHour
import com.example.android_template.data.Model.SunMoon
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchWeatherData(apiUrl: String): List<Current_Condition> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(apiUrl)
                .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                        val gson = Gson()
                        val listType = object : TypeToken<List<JsonObject>>() {}.type
                        val jsonArray: List<JsonObject> = gson.fromJson(responseBody, listType)

                        if (jsonArray.isNotEmpty()) {
                                val currentConditionJson = jsonArray[0]

                                return@withContext listOf(
                                        Current_Condition(
                                                Label =   "Nhiệt độ",
                                                Value = currentConditionJson.getAsJsonObject("Temperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                Unit = "°C"
                                        ),
                                        Current_Condition(

                                                Label ="Nhiệt độ cảm nhận",
                                                Value = currentConditionJson.getAsJsonObject("RealFeelTemperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                Unit = "°C"
                                        ),
                                        Current_Condition(

                                                Label = "Tốc độ gió",
                                                Value = currentConditionJson.getAsJsonObject("Wind")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                Unit =   " km/h"
                                        ),
                                        Current_Condition(

                                                Label = "Tốc độ gió lớn nhất",
                                                Value =   currentConditionJson.getAsJsonObject("WindGust")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                Unit =   " km/h"
                                        ),
                                        Current_Condition(

                                                Label =  "Độ ẩm",
                                                Value = currentConditionJson.get("RelativeHumidity").asString,
                                                Unit =  "%"
                                        ),
                                        Current_Condition(
                                                Label =   "Độ ẩm trong nhà",
                                                Value =   currentConditionJson.get("IndoorRelativeHumidity").asString,
                                                Unit =  "%"
                                        )
                                )
                        }
                }
        }
        return@withContext emptyList<Current_Condition>()
}

suspend fun fetSunMoon(apiUrl: String ) : List<Sun_Moon>  = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                        val gson = Gson()
                        val listType = object : TypeToken<List<JsonObject>>() {}.type
                        val jsonArray = gson.fromJson(responseBody, JsonObject :: class.java)
                        val Sunmoon  = jsonArray.getAsJsonArray("DailyForecasts")
                        if (Sunmoon!=null && Sunmoon.size()>0) {
                                val SunMoonJson = Sunmoon[0].asJsonObject
                                val Sun = SunMoonJson.getAsJsonObject("Sun")
                                val Moon = SunMoonJson.getAsJsonObject("Moon")

                                val SunRise =Sun.get("Rise").asString
                                val SunSet = Sun.get("Set").asString
                                val MoonRise = Moon.get("Rise").asString
                                val MoonSet = Moon.get("Set").asString

                                return@withContext listOf(Sun_Moon(Sun_or_Moon = "Sun", Rise = SunRise, Set = SunSet), Sun_Moon(Sun_or_Moon = "Moon", Rise = MoonRise, Set = MoonSet))
                        }
                }
        }
        return@withContext emptyList<Sun_Moon>()
}

suspend fun fetchForecastHour(apiUrl: String): List<Forecast_Hour> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                        val gson = Gson()
                        val listType = object : TypeToken<List<JsonObject>>() {}.type
                        val jsonArray: List<JsonObject> = gson.fromJson(responseBody, listType)
                        if (jsonArray.isNotEmpty()) {
                                val forecastList = mutableListOf<Forecast_Hour>()
                                for (jsonObject in jsonArray) {
                                        val dateTime = jsonObject.get("DateTime").asString
                                        val temperatureObject = jsonObject.getAsJsonObject("Temperature")
                                        val temperature = temperatureObject.get("Value").asString
                                        val precipitationProbability = jsonObject.get("PrecipitationProbability").asString
                                        forecastList.add(Forecast_Hour(forecast_time= dateTime,forecast_tem = temperature, forecast_rain = precipitationProbability))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<Forecast_Hour>()
}

suspend fun fetchForecastDay(apiUrl: String): List<Forecast_Day> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                        val gson = Gson()
                        val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
                        val dailyForecasts = jsonObject.getAsJsonArray("DailyForecasts")
                        if (dailyForecasts != null && dailyForecasts.size() > 0) {
                                val forecastList = mutableListOf<Forecast_Day>()
                                for (jsonElement in dailyForecasts) {
                                        val forecastJson = jsonElement.asJsonObject

                                        val dateDay = forecastJson.get("Date").asString

                                        val temperature = forecastJson.getAsJsonObject("Temperature")
                                        val minTemp = temperature.getAsJsonObject("Minimum").get("Value").asFloat
                                        val maxTemp = temperature.getAsJsonObject("Maximum").get("Value").asFloat

                                        val dayJson = forecastJson.getAsJsonObject("Day")
                                        val precipitationProbability = dayJson?.get("PrecipitationProbability")?.asInt ?: 0

                                        forecastList.add(Forecast_Day(day = dateDay,tem_min = minTemp.toString(), tem_max= maxTemp.toString(), rain =  precipitationProbability.toString()))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<Forecast_Day>()
}

suspend fun fetchHourlyFragment(apiUrl: String): List<Hourly_FragmentItem> = withContext(Dispatchers.IO){
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()
        val response = client.newCall(request).execute()
        if(response.isSuccessful){
                val responseBody = response.body?.string()
                if(responseBody!=null){
                        val gson = Gson()
                        val listHourly =object : TypeToken<List<JsonObject>>() {}.type
                        val jsonArray: List<JsonObject> = gson.fromJson(responseBody,listHourly)
                        if(jsonArray.isNotEmpty()){
                                val hourlyList = mutableListOf<Hourly_FragmentItem>()
                                for(jsonObject in jsonArray){
                                        val dateTime = jsonObject.get("DateTime").asString
                                        val t = jsonObject.getAsJsonObject("Temperature")
                                        val tem = t.get("Value").asString
                                        val t_rel = jsonObject.getAsJsonObject("RealFeelTemperature")
                                        val tem_rel = t_rel.get("Value").asString
                                        val pre = jsonObject.get("PrecipitationProbability").asString
                                         hourlyList.add(
                                                Hourly_FragmentItem(
                                                        hour = dateTime,
                                                        tem = tem,
                                                        rel_tem = tem_rel,
                                                        rain = pre,
                                                        day = dateTime
                                                )
                                                )
                                }
                                return@withContext hourlyList

                        }
                }
        }
        return@withContext emptyList<Hourly_FragmentItem>()
}
suspend fun fetDailyFragment(apiUrl: String): List<Daily_FragmentItem> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder().url(apiUrl).build()
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                        val gson = Gson()
                        val jsonObject = gson.fromJson(responseBody, JsonObject::class.java)
                        val dailyForecasts = jsonObject.getAsJsonArray("DailyForecasts")
                        if (dailyForecasts != null && dailyForecasts.size() > 0) {
                                val forecastList = mutableListOf<Daily_FragmentItem>()
                                for (jsonElement in dailyForecasts) {
                                        val forecastJson = jsonElement.asJsonObject

                                        val dateDay = forecastJson.get("Date").asString

                                        val temperature = forecastJson.getAsJsonObject("Temperature")
                                        val minTemp = temperature.getAsJsonObject("Minimum").get("Value").asFloat
                                        val maxTemp = temperature.getAsJsonObject("Maximum").get("Value").asFloat

                                        val dayJson = forecastJson.getAsJsonObject("Day")
                                        val precipitationProbability = dayJson?.get("PrecipitationProbability")?.asInt ?: 0

                                        forecastList.add(Daily_FragmentItem(day = dateDay.toString(), tem_min =  minTemp.toString(), tem_max =  maxTemp.toString(), rain = precipitationProbability.toString()))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<Daily_FragmentItem>()
}
