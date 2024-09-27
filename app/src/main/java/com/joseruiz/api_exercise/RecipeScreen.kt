package com.joseruiz.api_exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(idMeal: String?, modifier: Modifier = Modifier) {
    val recipeViewModel: MainViewModel = viewModel()

    // Llama a fetchRecipes si idMeal no es nulo
    if (idMeal != null) {
        recipeViewModel.onRecipeClick(idMeal)
    }

    val viewState by recipeViewModel.recipesState

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text("ERROR OCCURRED", modifier = Modifier.align(Alignment.Center))
            }

            else -> {
                RecipeDetailScreen(recipes = viewState.list)
            }
        }
    }
}

@Composable
fun RecipeDetailScreen(recipes: List<Recipe>) {
    // Mostrar los detalles de la primera receta en la lista o recorrer todas las recetas
    if (recipes.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Scroll para asegurar que se puedan ver todos los datos
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Aquí mostramos la primera receta como ejemplo, puedes adaptar para mostrar todas.
            val recipe = recipes.first()

            // Imagen centrada en la parte superior
            Image(
                painter = rememberAsyncImagePainter(recipe.strMealThumb),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Ajusta la altura de la imagen según sea necesario
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del platillo
            Text(
                text = recipe.strMeal,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Categoría y región
            Text(text = "Category: ${recipe.strCategory}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Area: ${recipe.strArea}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            // Instrucciones
            Text(
                text = "Instructions:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = recipe.strInstructions,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredientes y medidas
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Mostrar ingredientes y medidas de forma dinámica
            for (i in 1..20) {
                val ingredient = recipe.javaClass.getDeclaredField("strIngredient$i").get(recipe) as? String
                val measure = recipe.javaClass.getDeclaredField("strMeasure$i").get(recipe) as? String

                if (!ingredient.isNullOrEmpty() && !measure.isNullOrEmpty()) {
                    Text(text = "$ingredient: $measure", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fuente y YouTube (opcional)
            recipe.strSource?.let {
                Text(text = "Source: $it", style = MaterialTheme.typography.bodyMedium)
            }

            recipe.strYoutube?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Watch on YouTube",
                    color = Color.Blue,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable {
                        // Abre el enlace de YouTube
                        // Usa el contexto adecuado para abrir el navegador
                    }
                )
            }
        }
    } else {
        Text("No recipes available")
    }
}