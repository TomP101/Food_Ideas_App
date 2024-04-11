package com.example.android.foodideas.ViewModels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.foodideas.Data.Recipe
import com.example.android.foodideas.Data.RecipeRepository
import com.example.android.foodideas.Data.StorageItem
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update


class DeleteRecipeViewModel(private val recipeRepository: RecipeRepository): ViewModel() {

    private fun searchRecipe(text:String):StateFlow<RecipeListUiState> {
        return recipeRepository.searchRecipeStream(text).map { RecipeListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RecipeListUiState()
            )
    }

    val allRecipes: StateFlow<RecipeListUiState> =
        recipeRepository.getAllRecipesStream().map{ RecipeListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = RecipeListUiState()
            )
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _recipes = allRecipes

    var recipes = searchRecipe(" ")


    fun onSearchTextChange(text: String) {
        _searchText.value = text
        recipes = if(text.isNotBlank()){
            searchRecipe(text)
        }else{
            allRecipes
        }
    }
}


