package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DishViewModel : ViewModel() {

    private val _dishes = MutableStateFlow<List<Dish>>(
        listOf(
            // Put the two starter Dish objects from your handout here.
        )
    )

    val dishes: StateFlow<List<Dish>> = _dishes.asStateFlow()

    fun addDish(name: String) {
        if (name.isBlank()) return

        // TODO:
        // Create a new Dish with a unique ID
        // and add it to _dishes.
    }

    fun getDish(id: Int): Dish? {
        return _dishes.value.find { it.id == id }
    }

    fun updateDish(id: Int, newName: String) {
        if (newName.isBlank()) return

        // TODO 1:
        // Find the dish with the matching ID.
        // Create an updated copy with the new name.
        // Update _dishes.value.
    }

    fun deleteDish(id: Int) {

        // TODO 2:
        // Remove the dish with this ID.
        // Update _dishes.value.
    }

    fun addRecipe(dishId: Int, text: String) {
        if (text.isBlank()) return

        // TODO 3:
        // Find the correct dish.
        // Create a new Recipe with a unique ID.
        // Add it to that dish.
        // Update _dishes.value.
    }

    fun updateRecipe(
        dishId: Int,
        recipeId: Int,
        newText: String
    ) {
        if (newText.isBlank()) return

        // TODO 4:
        // Find the correct dish.
        // Find the correct recipe inside that dish.
        // Replace its text.
        // Update _dishes.value.
    }

    fun deleteRecipe(
        dishId: Int,
        recipeId: Int
    ) {

        // TODO 5:
        // Find the correct dish.
        // Remove the recipe with recipeId.
        // Update _dishes.value.
    }
}