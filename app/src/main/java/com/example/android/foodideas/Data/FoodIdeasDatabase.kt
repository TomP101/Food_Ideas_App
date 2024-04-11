package com.example.android.foodideas.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StorageItem::class,Recipe::class], version = 1, exportSchema = false)
abstract class FoodIdeasDatabase: RoomDatabase() {

    abstract fun StorageDao():Storagedao

    abstract fun RecipeDao():Recipedao

    companion object {
        @Volatile
        private var Instance: FoodIdeasDatabase? = null

        fun getDatabase(context: Context): FoodIdeasDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FoodIdeasDatabase::class.java, "foodideas_database")
                    .createFromAsset("database/Recipes.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}