package com.hadroy.moviestations.di

import com.hadroy.moviestations.data.MovieRepository

object Injection {

    fun provideMovieRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}