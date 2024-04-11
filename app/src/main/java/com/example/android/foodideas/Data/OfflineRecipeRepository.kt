package com.example.android.foodideas.Data

import kotlinx.coroutines.flow.Flow

class OfflineRecipeRepository(private val RecipeDao: Recipedao): RecipeRepository {

    override fun getAllRecipesStream(): Flow<List<Recipe>> = RecipeDao.getAllRecipes()

    override fun searchRecipeStream(search: String): Flow<List<Recipe>> = RecipeDao.searchRecipes(search)

    override suspend fun insertRecipe(Recipe: Recipe) = RecipeDao.insert(Recipe)

    override suspend fun deleteRecipe(Recipe: Recipe) = RecipeDao.delete(Recipe)

}