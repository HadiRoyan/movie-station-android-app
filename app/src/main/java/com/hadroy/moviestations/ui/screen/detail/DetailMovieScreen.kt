package com.hadroy.moviestations.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hadroy.moviestations.data.model.Movie
import com.hadroy.moviestations.di.Injection
import com.hadroy.moviestations.ui.ViewModelFactory
import com.hadroy.moviestations.ui.common.UiState
import com.hadroy.moviestations.ui.theme.MovieStationsTheme

@Composable
fun DetailMovieScreen(
    title: String,
    viewModel: DetailMovieViewModel = viewModel(factory = ViewModelFactory(Injection.provideMovieRepository())),
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getMovieByTitle(title)
            }

            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    Movie(
                        data.title,
                        data.year,
                        data.releaseDate,
                        data.storyLine,
                        data.posterUrl
                    ),
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .size(240.dp)
                .fillMaxWidth()
        )

        Text(
            text = "${movie.title} (${movie.year})",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 24.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Release at ${movie.releaseDate}",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Story Line",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(top = 16.dp, start = 8.dp),
        )

        Text(
            text = movie.storyLine,
            fontSize = 16.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
                .fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailMovieScreenPreview() {
    MovieStationsTheme {
        DetailContent(
            Movie(
                "Room",
                "2015",
                "2016-03-18",
                "",
                ""
            ),
        )
    }
}