package com.example.android.foodideas.Data


import android.content.Context


interface AppContainer {
    val storageRepository: StorageRepository
    val recipeRepository: RecipeRepository
}

class AppDataContainer(private val context: Context): AppContainer {

    override val storageRepository: StorageRepository by lazy {
        OfflineStorageRepository(FoodIdeasDatabase.getDatabase(context).StorageDao())
    }

    override val recipeRepository: RecipeRepository by lazy {
        OfflineRecipeRepository(FoodIdeasDatabase.getDatabase(context).RecipeDao())
    }

}