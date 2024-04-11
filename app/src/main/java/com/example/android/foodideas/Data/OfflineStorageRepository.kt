package com.example.android.foodideas.Data

import kotlinx.coroutines.flow.Flow

class OfflineStorageRepository(private val StorageDao: Storagedao): StorageRepository {
    override fun getAllItemsStream(): Flow<List<StorageItem>> = StorageDao.getAllItems()

    override fun getItemStream(id: Int): Flow<StorageItem?> = StorageDao.getItem(id)

    override suspend fun getItemByNameStream(name: String,unit:String): StorageItem? {
        return StorageDao.getItemByName(name,unit)
    }

    override suspend fun insertItem(StorageItem: StorageItem) = StorageDao.insert(StorageItem)

    override suspend fun updateItem(StorageItem: StorageItem) = StorageDao.update(StorageItem)

    override suspend fun deleteItem(StorageItem: StorageItem) = StorageDao.delete(StorageItem)

}