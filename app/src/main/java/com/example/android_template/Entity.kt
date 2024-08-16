package com.example.android_template

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Entity(tableName = "current_condition")
data class Current_Condition(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val Label: String,
    val Value: String,
    val Unit: String
)

@Entity(tableName = "sun_moon")
data class Sun_Moon(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val Sun_or_Moon : String,
    val Rise: String,
    val Set: String

)
@Entity(tableName = "forecast_hour")
data class Forecast_Hour(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,

    val forecast_time : String,
    val forecast_tem : String,
    val forecast_rain : String
)
@Entity(tableName = "forecast_day")
data class Forecast_Day(
    @PrimaryKey(autoGenerate = true) val id : Int =0,
    val day : String,
    val tem_min : String,
    val tem_max : String,
    val rain : String
)
@Entity(tableName = "hourly_fragmentitem")
data class Hourly_FragmentItem(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val city : String ,
    val hour : String,
    val tem : String,
    val rel_tem : String,
    val rain : String,
    val day : String
)
@Entity(tableName = "daily_fragmentitem")
data class Daily_FragmentItem(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val day : String,
    val tem_min : String,
    val tem_max : String,
    val rain : String
)
