package com.example.android.foodideas.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Storage")
data class StorageItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name:String,
    var quantity:String,
    var unit: String
)
