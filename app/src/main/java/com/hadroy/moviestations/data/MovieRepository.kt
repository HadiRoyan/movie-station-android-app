package com.hadroy.moviestations.data

import com.hadroy.moviestations.data.model.Movie
import com.hadroy.moviestations.data.model.MovieData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepository {

    private val movieList = mutableListOf<Movie>()

    init {
        if (movieList.isEmpty()) {
            MovieData.movies.forEach {
                movieList.add(it)
            }
        }
    }

    fun getMovies(): Flow<List<Movie>> {
        return flowOf(movieList)
    }

    fun getMovieByTitle(title: String): Movie {
        return movieList.first {
            it.title == title
        }
    }

    fun searchMovieByTitle(title: String): List<Movie> {
        return movieList.filter {
            it.title.contains(title, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository().apply {
                    instance = this
                }
            }
    }
}