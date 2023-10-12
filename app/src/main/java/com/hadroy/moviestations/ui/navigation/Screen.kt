package com.hadroy.moviestations.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object DetailMovie : Screen("movie/{title}") {
        fun createRoute(title: String) = "movie/$title"
    }
}
