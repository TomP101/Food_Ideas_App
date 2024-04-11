package com.example.android.foodideas.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.foodideas.AppViewModelProvider
import com.example.android.foodideas.R
import com.example.android.foodideas.ViewModels.HomeViewModel
import com.example.android.foodideas.ViewModels.RecipeDetails
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    AddRecipe(
        modifier = Modifier,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.saveRecipe()
                navigateBack()
            }
        },
        recipedetails = viewModel.recipeUiState.recipedetails,
        onValueChange = viewModel::updateUiState
            )

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddRecipe(
    modifier:Modifier = Modifier,
    onSaveClick: () -> Unit,
    recipedetails: RecipeDetails,
    onValueChange: (RecipeDetails) -> Unit = {}

){

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(25.dp))
        Image(painterResource(R.drawable.baseline_menu_book_24),"Menu book")
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            val keyboardController = LocalSoftwareKeyboardController.current
            BasicTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(horizontal = 4.dp)
                    .border(width = 1.5.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                value = recipedetails.name,
                onValueChange = { onValueChange(recipedetails.copy(name = it)) },
                keyboardActions = KeyboardActions( onDone = {keyboardController?.hide()}),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Column(


                        ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Recipe Name")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                        ) {
                            Icon(
                                Icons.Rounded.Edit,
                                contentDescription = "",
                                Modifier
                                    .padding(4.dp)
                                    .size(30.dp)

                            )
                            innerTextField()


                        }
                    }

                }
            )
        }

        Spacer(modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {

            val keyboardController = LocalSoftwareKeyboardController.current
            var maxlines by remember{mutableStateOf(1)}




            BasicTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(horizontal = 4.dp)
                    .border(width = 1.5.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                value = recipedetails.ingredients,
                onValueChange = {

                    onValueChange(recipedetails.copy(ingredients = it))
                },
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()},
                    onNext = {
                        maxlines++

                        val updatedText = "${recipedetails.ingredients}\n• "
                        onValueChange(recipedetails.copy(ingredients = updatedText))
                    }
                ),
                maxLines = maxlines,
                keyboardOptions = KeyboardOptions(imeAction= ImeAction.Next),
                decorationBox = { innerTextField ->
                    Column(
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            Arrangement.Center) {
                            Text(text = "Ingredients")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                        ) {
                            Icon(
                                Icons.Rounded.Edit,
                                contentDescription = "",
                                Modifier
                                    .padding(4.dp)
                                    .size(30.dp)

                            )
                            innerTextField()

                        }
                    }

                }
            )


        }
        Spacer(modifier.height(5.dp))
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {

            val keyboardController = LocalSoftwareKeyboardController.current
            var maxlines by remember{mutableStateOf(1)}




            BasicTextField(
                modifier = Modifier
                    .width(250.dp)
                    .padding(horizontal = 4.dp)
                    .border(width = 1.5.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                value = recipedetails.instruction,
                onValueChange = {

                    onValueChange(recipedetails.copy(instruction = it))
                                },
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()},
                    onNext = {
                        maxlines++

                        val updatedText = "${recipedetails.instruction}\n$maxlines. "
                        onValueChange(recipedetails.copy(instruction = updatedText))
                    }
                    ),
                maxLines = maxlines,
                keyboardOptions = KeyboardOptions(imeAction= ImeAction.Next),
                decorationBox = { innerTextField ->
                    Column(
                        ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                            Arrangement.Center) {
                            Text(text = "Recipe Steps")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                        ) {
                            Icon(
                                Icons.Rounded.Edit,
                                contentDescription = "",
                                Modifier
                                    .padding(4.dp)
                                    .size(30.dp)

                            )
                            innerTextField()

                        }
                    }

                }
            )


        }
        Button(onClick = onSaveClick){
            Image(painterResource(R.drawable.outline_add_task_24),"")

        }


    }


}


