package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DishListScreen(
    viewModel: DishViewModel,
    onDishClick: (Int) -> Unit
) {
    val dishes by viewModel.dishes.collectAsState()

    var newDishName by remember {
        mutableStateOf("")
    }

    var editingDish by remember {
        mutableStateOf<Dish?>(null)
    }

    var editName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("My Dishes")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedTextField(
                value = newDishName,
                onValueChange = {
                    newDishName = it
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("New dish name")
                }
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                onClick = {
                    // Add the dish using the ViewModel.
                    // Then clear the input.
                }
            ) {
                Text("Add")
            }
        }

        LazyColumn {

            items(dishes) { dish ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            onDishClick(dish.id)
                        }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(dish.name)

                        Text(
                            "${dish.recipes.size} step(s)"
                        )

                        Row {

                            TextButton(
                                onClick = {
                                    editingDish = dish
                                    editName = dish.name
                                }
                            ) {
                                Text("Edit")
                            }

                            TextButton(
                                onClick = {
                                    // TODO 7:
                                    // Delete this dish.
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }

    if (editingDish != null) {

        AlertDialog(
            onDismissRequest = {
                editingDish = null
            },

            title = {
                Text("Rename Dish")
            },

            text = {
                OutlinedTextField(
                    value = editName,
                    onValueChange = {
                        editName = it
                    }
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        // TODO 8:
                        // Update the selected dish name.
                        // Then close the dialog.
                    }
                ) {
                    Text("Save")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        editingDish = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}