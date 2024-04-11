package com.example.android.foodideas.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.foodideas.Data.StorageItem
import com.example.android.foodideas.Data.StorageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ItemUiState(
    val itemdetails: ItemDetails = ItemDetails(0,"","1","piece")
)

data class StorageUiState(
    val storageList: List<StorageItem> = listOf()
)

data class ItemDetails(
    var id : Int = 0,
    val name: String = "",
    val quantity : String = "",
    val unit: String = ""
)

fun ItemDetails.toStorageItem(): StorageItem = StorageItem(
    id = id,
    name = name,
    quantity = quantity,
    unit = unit
)



fun StorageItem.toItemUiState(): ItemUiState = ItemUiState(
    itemdetails = this.toItemDetails(),
)

fun StorageItem.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    name = name,
    quantity = quantity,
    unit = unit
)

fun ChangeCommasintoDots(word: String): String{
    return word.replace(",",".")
}


class StorageViewModel(private val storageRepository: StorageRepository) : ViewModel() {

    var itemUiState: ItemUiState by mutableStateOf(ItemUiState())

    fun updateUiState(itemdetails: ItemDetails) {
        itemUiState = ItemUiState(itemdetails = itemdetails)
    }

    val storageUiState: StateFlow<StorageUiState> =
        storageRepository.getAllItemsStream().map{ StorageUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = StorageUiState()
            )

    suspend fun deleteItem(StorageItem: StorageItem){
        storageRepository.deleteItem(StorageItem)
    }

    suspend fun saveItem() {

        val itemDetails = itemUiState.itemdetails
        val existingItem = storageRepository.getItemByNameStream(itemDetails.name,itemDetails.unit)
        if (existingItem != null) {
            val num1 = ChangeCommasintoDots(existingItem.quantity)
            val num2 = ChangeCommasintoDots(itemDetails.quantity)
            val updatedQuantity = num1.toDouble() + num2.toDouble()
            val updatedItem = existingItem.copy(quantity = updatedQuantity.toString())
            storageRepository.updateItem(updatedItem)
        } else {
            val newItem = itemDetails.toStorageItem()
            storageRepository.insertItem(newItem)
        }


    }
}