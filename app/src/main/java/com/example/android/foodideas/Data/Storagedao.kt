package com.example.android.foodideas.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface Storagedao {

    @Query("SELECT * FROM Storage WHERE id = :id")
    fun getItem(id: Int): Flow<StorageItem>

    @Query("SELECT * FROM Storage")
    fun getAllItems(): Flow<List<StorageItem>>

    @Query("SELECT * FROM Storage WHERE name = :name AND unit = :unit")
    suspend fun getItemByName(name:String, unit:String): StorageItem?

    @Insert(entity = StorageItem::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(StorageItem: StorageItem)

    @Update
    suspend fun update(StorageItem: StorageItem)

    @Delete
    suspend fun delete(StorageItem: StorageItem)

}