package com.example.android.foodideas.Data

import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getAllRecipesStream(): Flow<List<Recipe>>

    fun searchRecipeStream(search:String): Flow<List<Recipe>>

    suspend fun insertRecipe(Recipe: Recipe)

    suspend fun deleteRecipe(Recipe: Recipe)

}