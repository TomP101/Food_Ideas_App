package com.example.android.foodideas

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.foodideas.Screens.AddRecipeScreen
import com.example.android.foodideas.Screens.DeleteRecipeScreen
import com.example.android.foodideas.Screens.HomeScreen
import com.example.android.foodideas.Screens.StorageScreen

enum class FoodIdeasScreens(){
    Home,
    Storage,
    AddRecipe,
    DeleteRecipe
}




@Composable
fun FoodIdeasApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController =navController,
        startDestination =FoodIdeasScreens.Home.name,
        modifier = modifier
    ){

        composable(route= FoodIdeasScreens.Home.name){
            HomeScreen(
                onStorageButtonClicked = {navController.navigate(FoodIdeasScreens.Storage.name)},
                onAddRecipeButtonClicked = {navController.navigate(FoodIdeasScreens.AddRecipe.name)},
                onDeleteRecipeButtonClicked = {navController.navigate(FoodIdeasScreens.DeleteRecipe.name)}
            )
        }
        composable(route = FoodIdeasScreens.Storage.name){
            StorageScreen(navigateBack = {navController.popBackStack()})
        }
        composable(route = FoodIdeasScreens.AddRecipe.name){
            AddRecipeScreen(navigateBack = {navController.popBackStack()})
        }
        composable(route= FoodIdeasScreens.DeleteRecipe.name){
            DeleteRecipeScreen(navigateBack = {navController.popBackStack()})
        }


    }



}