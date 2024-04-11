package com.example.android.foodideas.Data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String,
    var ingredients:String,
    var instruction: String
)
