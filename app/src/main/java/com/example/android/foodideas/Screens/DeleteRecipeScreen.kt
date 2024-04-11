package com.example.android.foodideas.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.foodideas.AppViewModelProvider
import com.example.android.foodideas.Data.Recipe
import com.example.android.foodideas.R
import com.example.android.foodideas.ViewModels.DeleteRecipeViewModel
import com.example.android.foodideas.ViewModels.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRecipeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: DeleteRecipeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    HomeviewModel : HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {
    val searchText by viewModel.searchText.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            DeleteRecipeList(modifier = modifier.wrapContentSize(),
                itemList = recipes.recipeList,
                viewModel = HomeviewModel)
            }
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeleteRecipeList(modifier: Modifier,
               itemList: List<Recipe>,
               viewModel: HomeViewModel
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= modifier

    ) {


        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            columns = StaggeredGridCells.Adaptive(180.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            items(itemList) {item ->
                DeleteRecipe(recipe= item, viewModel = viewModel)
            }
        }
    }

}


@Composable
fun DeleteRecipe(recipe: Recipe, modifier: Modifier = Modifier, viewModel: HomeViewModel) {

    Row(modifier = Modifier.wrapContentSize()) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(128.dp)
            .wrapContentSize()
            .weight(6f),
        colors = CardDefaults.cardColors(
            containerColor = RandomColor()
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(size = 16.dp)
    )
    {

            Text(
                text = recipe.name,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.White
            )


        }
        val coroutineScope = rememberCoroutineScope()
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.deleteRecipe(recipe)
                }
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White
            ), modifier = Modifier
                .weight(1f)
        ) {
            Image(
                painterResource(R.drawable.baseline_delete_forever_24),
                "Menu book",
                modifier = Modifier.fillMaxSize()
            )
        }

    }
}


