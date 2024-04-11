package com.example.android.foodideas.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.foodideas.AppViewModelProvider
import com.example.android.foodideas.Data.StorageItem
import com.example.android.foodideas.ViewModels.ItemDetails
import com.example.android.foodideas.R
import com.example.android.foodideas.ViewModels.StorageViewModel
import kotlinx.coroutines.launch

@Composable
fun StorageScreen(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    viewModel: StorageViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope =rememberCoroutineScope()


    AddToStorage(
            modifier = Modifier,
            onCancelClick = { navigateBack() },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                }
            },
            itemdetails = viewModel.itemUiState.itemdetails,
            onValueChange = viewModel::updateUiState,
            viewModel = viewModel
        )



}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddToStorage(modifier: Modifier,
                 viewModel: StorageViewModel,
                 onCancelClick: () -> Unit,
                 onSaveClick: () -> Unit,
                 itemdetails: ItemDetails,
                 onValueChange: (ItemDetails) -> Unit = {}

                 ){

    var isExpanded by remember { mutableStateOf(false) }

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
                    .width(230.dp)
                    .padding(horizontal = 20.dp)
                    .border(width = 1.5.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                value = itemdetails.name,
                onValueChange = { onValueChange(itemdetails.copy(name = it)) },
                keyboardActions = KeyboardActions( onDone = {keyboardController?.hide()}),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(text = "Product")
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

                            Spacer(modifier.width(2.dp))

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
            BasicTextField(
                modifier = Modifier
                    .width(80.dp)
                    .padding(horizontal = 4.dp)
                    .border(width = 1.5.dp, color = Color.Black, RoundedCornerShape(12.dp)),
                value = itemdetails.quantity,
                onValueChange = { onValueChange(itemdetails.copy(quantity = it)) },
                keyboardActions = KeyboardActions( onDone = {keyboardController?.hide()}),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,imeAction=ImeAction.Done),
                decorationBox = { innerTextField ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Text(text = "Quantity")
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
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .width(110.dp)
                    .height(60.dp),
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it })
            {
                TextField(
                    value = itemdetails.unit,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false })
                {
                    DropdownMenuItem(
                        text = { Text("g") },
                        onClick = {
                            onValueChange(itemdetails.copy(unit = "g"))
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text("kg") },
                        onClick = {
                            onValueChange(itemdetails.copy(unit = "kg"))
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text("piece") },
                        onClick = {
                            onValueChange(itemdetails.copy(unit = "piece"))
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text("l") },
                        onClick = {
                            onValueChange(itemdetails.copy(unit = "l"))
                            isExpanded = false
                        })
                    DropdownMenuItem(
                        text = { Text("ml") },
                        onClick = {
                            onValueChange(itemdetails.copy(unit = "ml"))
                            isExpanded = false
                        })
                }
            }

        }

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ){

            Button(onClick = onCancelClick){
                Image(painterResource(R.drawable.baseline_no_food_24),"")
            }

            Spacer(modifier = Modifier.width(5.dp))

            Button(onClick = onSaveClick){
                Image(painterResource(R.drawable.baseline_fastfood_24),"")
            }

        }

        val storageUiState by viewModel.storageUiState.collectAsState()

        StorageList(modifier = modifier.fillMaxSize(),
            itemList = storageUiState.storageList,
            viewModel = viewModel
        )



    }

}

@Composable
fun StorageList(modifier: Modifier = Modifier,
                itemList: List<StorageItem>,
                viewModel: StorageViewModel
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier= modifier

    ) {


        LazyColumn(modifier = Modifier.fillMaxHeight()){
            items(items = itemList, itemContent = {item ->
                StorageItem(item = item,viewModel = viewModel)
            })
        }
    }

}

@Composable
fun StorageItem(item: StorageItem,modifier: Modifier = Modifier,viewModel: StorageViewModel) {

    Row() {

        Card(
            modifier = modifier
                .weight(5f)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )

        ) {
            Column(
                modifier = Modifier.padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier.weight(5f))


                    Text(
                        text = "x" + item.quantity + "   ",
                        style = MaterialTheme.typography.titleLarge
                    )



                    Text(
                        text = (item.unit),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

        val coroutineScope = rememberCoroutineScope()


        IconButton(onClick = {
            coroutineScope.launch {
                viewModel.deleteItem(item)
            }
        },
            colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.White
        ), modifier = Modifier
            .weight(1f)){
            Image(painterResource(R.drawable.baseline_delete_forever_24),
                "Menu book",
                modifier = Modifier.fillMaxSize()
            )
        }

    }

}
