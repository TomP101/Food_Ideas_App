package com.example.android.foodideas

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.foodideas.ViewModels.DeleteRecipeViewModel
import com.example.android.foodideas.ViewModels.HomeViewModel
import com.example.android.foodideas.ViewModels.StorageViewModel

object AppViewModelProvider {
    var Factory = viewModelFactory{
        initializer {
            this.createSavedStateHandle()
            StorageViewModel(
                foodideasApplication().container.storageRepository)
        }

        initializer{
            this.createSavedStateHandle()
            HomeViewModel(
                foodideasApplication().container.recipeRepository
            )
        }
        initializer{
            this.createSavedStateHandle()
            DeleteRecipeViewModel(
                foodideasApplication().container.recipeRepository
            )
        }


    }

    fun CreationExtras.foodideasApplication(): FoodIdeasApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FoodIdeasApplication)

}