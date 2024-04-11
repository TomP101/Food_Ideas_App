package com.example.android.foodideas.Data

import kotlinx.coroutines.flow.Flow

interface StorageRepository {

    fun getAllItemsStream(): Flow<List<StorageItem>>

    fun getItemStream(id: Int): Flow<StorageItem?>

    suspend fun getItemByNameStream(name:String,unit:String): StorageItem?

    suspend fun insertItem(StorageItem: StorageItem)

    suspend fun updateItem(StorageItem: StorageItem)

    suspend fun deleteItem(StorageItem: StorageItem)


}