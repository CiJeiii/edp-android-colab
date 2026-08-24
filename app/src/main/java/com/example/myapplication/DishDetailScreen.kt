package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
fun DishDetailScreen(
    dishId: Int,
    viewModel: DishViewModel,
    onBack: () -> Unit
) {
    val dishes by viewModel.dishes.collectAsState()

    val dish = dishes.find {
        it.id == dishId
    }

    var newStep by remember {
        mutableStateOf("")
    }

    var editingRecipe by remember {
        mutableStateOf<Recipe?>(null)
    }

    var editText by remember {
        mutableStateOf("")
    }

    if (dish == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Dish not found")

            Button(
                onClick = onBack
            ) {
                Text("Back")
            }
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(dish.name)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = newStep,
                onValueChange = {
                    newStep = it
                },
                modifier = Modifier.weight(1f),
                label = {
                    Text("New step")
                }
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                onClick = {
                    // TODO 9:
                    // Add the new recipe step.
                    // Then clear the input.
                }
            ) {
                Text("Add")
            }
        }

        LazyColumn {

            itemsIndexed(dish.recipes) { index, recipe ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Text("${index + 1}. ${recipe.text}")

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    TextButton(
                        onClick = {
                            editingRecipe = recipe
                            editText = recipe.text
                        }
                    ) {
                        Text("Edit")
                    }

                    TextButton(
                        onClick = {
                            // TODO 10:
                            // Delete this recipe.
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        Button(
            onClick = onBack
        ) {
            Text("Back")
        }
    }

    if (editingRecipe != null) {

        AlertDialog(
            onDismissRequest = {
                editingRecipe = null
            },

            title = {
                Text("Edit Step")
            },

            text = {
                OutlinedTextField(
                    value = editText,
                    onValueChange = {
                        editText = it
                    }
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        // TODO 11:
                        // Update the selected recipe.
                        // Then close the dialog.
                    }
                ) {
                    Text("Save")
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        editingRecipe = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}