package com.example.pokedex.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.pokedex.data.remote.responses.Pokemon
import com.example.pokedex.util.Resource
import com.example.pokedex.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    dominantColor: Color,
    pokemonName: String,
    pokemonImageUrl: String,
    navController: NavController,
    topPadding: Dp = 20.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.2f)
                .align(Alignment.TopStart)
        )


        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if (pokemonInfo is Resource.Success) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(pokemonImageUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemonName,
                    modifier = Modifier
                        .size(200.dp)
                        .offset(y = topPadding)
                )
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(x = 16.dp, y = 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}