package com.example.android.foodideas.Screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.foodideas.AppViewModelProvider
import com.example.android.foodideas.Data.Recipe
import com.example.android.foodideas.ViewModels.HomeViewModel
import com.example.android.foodideas.ui.theme.Almond
import com.example.android.foodideas.ui.theme.Blue
import com.example.android.foodideas.ui.theme.DarkPurple
import com.example.android.foodideas.ui.theme.DarkRed
import com.example.android.foodideas.ui.theme.DeepTeal
import com.example.android.foodideas.ui.theme.Olive
import com.example.android.foodideas.ui.theme.Orange
import com.example.android.foodideas.ui.theme.Peach
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStorageButtonClicked: () -> Unit,
    onAddRecipeButtonClicked: () -> Unit,
    onDeleteRecipeButtonClicked: () -> Unit,

    homeviewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory )
){
    var isPopupVisible by remember { mutableStateOf(false) }
    val recipes by homeviewModel.allRecipes.collectAsState()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Food Ideas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                    )
                },
                navigationIcon = { ToppAppMenu(
                    onStorageButtonClicked =onStorageButtonClicked,
                    onAddRecipeButtonClicked = onAddRecipeButtonClicked,
                    onDeleteRecipeButtonClicked = onDeleteRecipeButtonClicked)

                }
            )
        }
    ){ innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ){


                RecipeList(modifier = modifier.wrapContentSize(),
                    itemList = recipes.recipeList,
                    viewModel = homeviewModel)
            }
        }
}


@Composable
fun ToppAppMenu(onStorageButtonClicked: () -> Unit,
                onAddRecipeButtonClicked: () -> Unit,
                onDeleteRecipeButtonClicked: () -> Unit
    ){

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("View Storage") },
                onClick = onStorageButtonClicked
            )
            DropdownMenuItem(
                text = { Text("Add a Recipe") },
                onClick = onAddRecipeButtonClicked
            )
            DropdownMenuItem(
                text = { Text("Delete a Recipe") },
                onClick = onDeleteRecipeButtonClicked
            )
        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeList(modifier: Modifier,
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
                Recipe(
                    recipe= item,
                    viewModel = viewModel)
            }
        }
    }

    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Recipe(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    var showPopup by remember { mutableStateOf(false) }
    val instruction = remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(128.dp)
            .wrapContentSize(),
        onClick = {
            showPopup = true
            instruction.value = recipe.instruction // Set instruction to show
        },
        colors = CardDefaults.cardColors(
            containerColor = RandomColor()
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(size = 16.dp)
    ) {
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

    if (showPopup) {
        Popup(alignment = Alignment.Center) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 35.dp)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column {
                    IconButton(
                        onClick = { showPopup = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                    Text(
                        text = instruction.value,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}






fun RandomColor(): Color {
    val ColorList = listOf(DarkRed,Orange,Olive,Blue, DeepTeal, Peach, Almond, DarkPurple)
    val random = Random.nextInt(0,8)
    return ColorList[random]
}



