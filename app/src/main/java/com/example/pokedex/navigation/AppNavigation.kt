package com.example.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.screens.PokemonDetailScreen
import com.example.pokedex.screens.PokemonListScreen
import java.util.Locale

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.POKEMON_LIST_SCREEN) {
        composable(Routes.POKEMON_LIST_SCREEN) {
            PokemonListScreen(navController = navController)
        }
        composable("${Routes.POKEMON_DETAIL_SCREEN}/{dominantColor}/{pokemonName}/{pokemonImageUrl}",
            arguments = listOf(
                navArgument("dominantColor") {
                    type = NavType.IntType
                },
                navArgument("pokemonName") {
                    type = NavType.StringType
                },
                navArgument("pokemonImageUrl") {
                    type = NavType.StringType
                }
            )
        ) {
            val dominantColor = remember {
                val color = it.arguments?.getInt("dominantColor")
                color?.let { Color(it) } ?: Color.White
            }
            val pokemonName = remember {
                it.arguments?.getString("pokemonName")
            }
            val pokemonImageUrl = remember {
                it.arguments?.getString("pokemonImageUrl")
            }
            PokemonDetailScreen(
                dominantColor = dominantColor,
                pokemonName = pokemonName?.lowercase(Locale.ROOT) ?: "",
                pokemonImageUrl = pokemonImageUrl ?: "https://upload.wikimedia.org/wikipedia/commons/9/98/International_Pok%C3%A9mon_logo.svg",
                navController = navController
            )
        }
    }
}