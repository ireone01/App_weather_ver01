package com.example.android_template

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Dao
interface CurrentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(condition: List<Current_Condition>)

    @Query("SELECT * FROM current_condition")
    suspend fun getAllConditions(): List<Current_Condition>

    @Query("DELETE FROM current_condition")
    suspend fun clearAll()
}

@Dao
interface SunMoonDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insterAll(condition: List<Sun_Moon>)

    @Query("SELECT * FROM sun_moon")
    suspend fun getAllConditions(): List<Sun_Moon>

    @Query("DELETE FROM sun_moon")
    suspend fun clearAll()
}

@Dao
interface ForecastHourDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insterAll(condition: List<Forecast_Hour>)

    @Query("SELECT * FROM forecast_hour")
    suspend fun getAllConditions(): List<Forecast_Hour>

    @Query("DELETE FROM forecast_hour")
    suspend fun clearAll()
}

@Dao
interface ForecastDayDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insterAll(condition: List<Forecast_Day>)

    @Query("SELECT * FROM forecast_day")
    suspend fun getAllConditions(): List<Forecast_Day>

    @Query("DELETE FROM forecast_day")
    suspend fun clearAll()
}

@Dao
interface HourlyFragmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insterAll(condition: List<Hourly_FragmentItem>)

    @Query("SELECT  * FROM hourly_fragmentitem")
    suspend fun getAllConditions(): List<Hourly_FragmentItem>

    @Query("DELETE FROM hourly_fragmentitem")
    suspend fun clearAll()
}

@Dao
interface DailyFragmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insterAll(condition: List<Daily_FragmentItem>)

    @Query("SELECT * FROM daily_fragmentitem")
    suspend fun getAllConditions(): List<Daily_FragmentItem>

    @Query("DELETE FROM daily_fragmentitem")
    suspend fun clearAll()
}
@Database(entities = [Current_Condition::class,
    Sun_Moon::class,
    Forecast_Hour::class ,
    Forecast_Day::class,
    Hourly_FragmentItem::class,
    Daily_FragmentItem::class] , version = 2)
abstract class AppDatabase : RoomDatabase(){
    abstract fun currentDAO() : CurrentDAO
    abstract fun sunmoonDAO() : SunMoonDAO
    abstract fun forecasthourDAO() : ForecastHourDAO
    abstract fun forecastdayDAO() : ForecastDayDAO
    abstract fun hourlyfragmentDAO() : HourlyFragmentDAO
    abstract fun dailyfragmentDAO() : DailyFragmentDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}