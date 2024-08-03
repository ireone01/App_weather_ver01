package com.example.android_template.Data

import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun fetchWeatherData(apiUrl: String): List<CurrentCondition> = withContext(Dispatchers.IO) {
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
                                        CurrentCondition(
                                                "Nhiệt độ",
                                                currentConditionJson.getAsJsonObject("Temperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                "°" + currentConditionJson.getAsJsonObject("Temperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Unit").asString
                                        ),
                                        CurrentCondition(
                                                "Nhiệt độ cảm nhận",
                                                currentConditionJson.getAsJsonObject("RealFeelTemperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                "°" + currentConditionJson.getAsJsonObject("RealFeelTemperature")
                                                        .getAsJsonObject("Metric")
                                                        .get("Unit").asString
                                        ),
                                        CurrentCondition(
                                                "Tốc độ gió",
                                                currentConditionJson.getAsJsonObject("Wind")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                " " + currentConditionJson.getAsJsonObject("Wind")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Unit").asString
                                        ),
                                        CurrentCondition(
                                                "Tốc độ gió lớn nhất",
                                                currentConditionJson.getAsJsonObject("WindGust")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Value").asString,
                                                " " + currentConditionJson.getAsJsonObject("WindGust")
                                                        .getAsJsonObject("Speed")
                                                        .getAsJsonObject("Metric")
                                                        .get("Unit").asString
                                        ),
                                        CurrentCondition(
                                                "Độ ẩm",
                                                currentConditionJson.get("RelativeHumidity").asString,
                                                "%"
                                        ),
                                        CurrentCondition(
                                                "Độ ẩm trong nhà",
                                                currentConditionJson.get("IndoorRelativeHumidity").asString,
                                                "%"
                                        )
                                )
                        }
                }
        }
        return@withContext emptyList<CurrentCondition>()
}

suspend fun fetSunMoon(apiUrl: String ) : List<SunMoon>  = withContext(Dispatchers.IO) {
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

                                return@withContext listOf(SunMoon("Sun",SunRise,SunSet), SunMoon("Moon",MoonRise,MoonSet))
                        }
                }
        }
        return@withContext emptyList<SunMoon>()
}

suspend fun fetchForecastHour(apiUrl: String): List<ForecastHour> = withContext(Dispatchers.IO) {
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
                                val forecastList = mutableListOf<ForecastHour>()
                                for (jsonObject in jsonArray) {
                                        val dateTime = jsonObject.get("DateTime").asString
                                        val temperatureObject = jsonObject.getAsJsonObject("Temperature")
                                        val temperature = temperatureObject.get("Value").asString
                                        val precipitationProbability = jsonObject.get("PrecipitationProbability").asString
                                        forecastList.add(ForecastHour(dateTime, temperature, precipitationProbability))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<ForecastHour>()
}

suspend fun fetchForecastDay(apiUrl: String): List<ForecastDay> = withContext(Dispatchers.IO) {
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
                                val forecastList = mutableListOf<ForecastDay>()
                                for (jsonElement in dailyForecasts) {
                                        val forecastJson = jsonElement.asJsonObject

                                        val dateDay = forecastJson.get("Date").asString

                                        val temperature = forecastJson.getAsJsonObject("Temperature")
                                        val minTemp = temperature.getAsJsonObject("Minimum").get("Value").asFloat
                                        val maxTemp = temperature.getAsJsonObject("Maximum").get("Value").asFloat

                                        val dayJson = forecastJson.getAsJsonObject("Day")
                                        val precipitationProbability = dayJson?.get("PrecipitationProbability")?.asInt ?: 0

                                        forecastList.add(ForecastDay(dateDay, minTemp.toString(), maxTemp.toString(), precipitationProbability.toString()))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<ForecastDay>()
}

suspend fun fetchHourlyFragment(apiUrl: String): List<HourlyFragmentItem> = withContext(Dispatchers.IO){
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
                                val hourlyList = mutableListOf<HourlyFragmentItem>()
                                for(jsonObject in jsonArray){
                                        val dateTime = jsonObject.get("DateTime").asString
                                        val t = jsonObject.getAsJsonObject("Temperature")
                                        val tem = t.get("Value").asString
                                        val t_rel = jsonObject.getAsJsonObject("RealFeelTemperature")
                                        val tem_rel = t_rel.get("Value").asString
                                        val pre = jsonObject.get("PrecipitationProbability").asString
                                        hourlyList.add(HourlyFragmentItem(dateTime,tem, tem_rel,pre,dateTime))
                                }
                                return@withContext hourlyList

                        }
                }
        }
        return@withContext emptyList<HourlyFragmentItem>()
}
suspend fun fetDailyFragment(apiUrl: String): List<DailyFragmentItem> = withContext(Dispatchers.IO) {
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
                                val forecastList = mutableListOf<DailyFragmentItem>()
                                for (jsonElement in dailyForecasts) {
                                        val forecastJson = jsonElement.asJsonObject

                                        val dateDay = forecastJson.get("Date").asString

                                        val temperature = forecastJson.getAsJsonObject("Temperature")
                                        val minTemp = temperature.getAsJsonObject("Minimum").get("Value").asFloat
                                        val maxTemp = temperature.getAsJsonObject("Maximum").get("Value").asFloat

                                        val dayJson = forecastJson.getAsJsonObject("Day")
                                        val precipitationProbability = dayJson?.get("PrecipitationProbability")?.asInt ?: 0

                                        forecastList.add(DailyFragmentItem(dateDay.toString(), minTemp.toString(), maxTemp.toString(), precipitationProbability.toString()))
                                }
                                return@withContext forecastList
                        }
                }
        }
        return@withContext emptyList<DailyFragmentItem>()
}
