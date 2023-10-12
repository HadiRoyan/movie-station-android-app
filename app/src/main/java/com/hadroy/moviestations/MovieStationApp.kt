package com.hadroy.moviestations

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hadroy.moviestations.ui.component.TopBar
import com.hadroy.moviestations.ui.navigation.Screen
import com.hadroy.moviestations.ui.screen.detail.DetailMovieScreen
import com.hadroy.moviestations.ui.screen.home.HomeScreen
import com.hadroy.moviestations.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieStationApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            if (currentRoute != Screen.Home.route) {
                TopBar(
                    title = if (currentRoute == Screen.DetailMovie.route) "Detail" else "About Me",
                    navigate = {
                        navController.navigateUp()
                    },
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { title ->
                        navController.navigate(Screen.DetailMovie.createRoute(title))
                    },
                    navigateToProfile = {
                        navController.navigate(Screen.Profile.route)
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("title") { type = NavType.StringType }),
            ) {
                val title = it.arguments?.getString("title") ?: ""
                DetailMovieScreen(title = title)
            }
        }
    }
}

@Preview
@Composable
fun MovieStationAppPreview() {
    MaterialTheme {
        Surface {
            MovieStationApp()
        }
    }
}

