package com.example.android.foodideas.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.foodideas.Data.Recipe
import com.example.android.foodideas.Data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class RecipeUiState(
    val recipedetails: RecipeDetails = RecipeDetails(0,"","•","1.")
)

data class RecipeListUiState(
    val recipeList: List<Recipe> = listOf()
)

data class RecipeDetails(
    var id : Int = 0,
    val name: String = "",
    val ingredients : String = "•",
    val instruction: String = "1."
)

fun RecipeDetails.toRecipeItem(): Recipe = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    instruction = instruction
)

fun Recipe.toRecipeUiState(): RecipeUiState = RecipeUiState(
    recipedetails = this.toRecipeDetails(),
)

fun Recipe.toRecipeDetails(): RecipeDetails = RecipeDetails(
    id = id,
    name = name,
    ingredients = ingredients,
    instruction = instruction
)

class HomeViewModel(private val recipeRepository: RecipeRepository): ViewModel() {


    var recipeUiState: RecipeUiState by mutableStateOf(RecipeUiState())



    fun updateUiState(recipedetails: RecipeDetails) {
        recipeUiState = RecipeUiState(recipedetails = recipedetails)
    }


    val allRecipes: StateFlow<RecipeListUiState> =
        recipeRepository.getAllRecipesStream().map{ RecipeListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RecipeListUiState()
            )




    suspend fun deleteRecipe(Recipe: Recipe){
        recipeRepository.deleteRecipe(Recipe)
    }

    suspend fun saveRecipe() {
        val recipeDetails = recipeUiState.recipedetails
        val newRecipe = recipeDetails.toRecipeItem()
        recipeRepository.insertRecipe(newRecipe)
    }


}

