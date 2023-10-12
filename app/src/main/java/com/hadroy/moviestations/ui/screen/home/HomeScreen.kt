package com.hadroy.moviestations.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hadroy.moviestations.data.model.Movie
import com.hadroy.moviestations.di.Injection
import com.hadroy.moviestations.ui.ViewModelFactory
import com.hadroy.moviestations.ui.common.UiState
import com.hadroy.moviestations.ui.component.MovieItem
import com.hadroy.moviestations.ui.component.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideMovieRepository())),
    navigateToDetail: (String) -> Unit,
    navigateToProfile: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getALlMovies()
            }

            is UiState.Success -> {
                HomeContent(
                    listMovies = uiState.data,
                    viewModel = viewModel,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    navigateToProfile = navigateToProfile
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    listMovies: List<Movie>,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToProfile: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        val query by viewModel.query

        item {
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
                onTrailingIconClick = navigateToProfile
            )
        }
        items(listMovies, key = { it.title }) {
            MovieItem(
                title = it.title,
                year = it.year,
                posterUrl = it.posterUrl,
                modifier = modifier
                    .clickable { navigateToDetail(it.title) }
            )
            Divider()
        }
    }
}