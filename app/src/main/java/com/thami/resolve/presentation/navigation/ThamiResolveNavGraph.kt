package com.thami.resolve.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.thami.resolve.presentation.home.HomeScreen
import com.thami.resolve.presentation.shoppinglist.ShoppingListScreen

@Composable
fun ThamiResolveNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Home,
        enterTransition = { fadeIn(animationSpec = tween(300)) + slideInHorizontally { it / 4 } },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) + slideOutHorizontally { it / 4 } }
    ) {
        composable<Home> {
            HomeScreen(
                onOpenList = { listId -> navController.navigate(ShoppingListDetail(listId)) }
            )
        }
        composable<ShoppingListDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<ShoppingListDetail>()
            ShoppingListScreen(
                listId = args.listId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
