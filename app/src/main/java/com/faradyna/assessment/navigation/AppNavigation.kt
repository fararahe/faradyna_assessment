package com.faradyna.assessment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.faradyna.assessment.R
import com.faradyna.assessment.ui.common.ErrorScreen
import com.faradyna.assessment.ui.screens.cart.CartScreen
import com.faradyna.assessment.ui.screens.checkout.CheckOutScreen
import com.faradyna.assessment.ui.screens.home.HomeScreen
import com.faradyna.assessment.ui.screens.login.LoginScreen
import com.faradyna.assessment.ui.screens.main.SplashScreen
import com.faradyna.assessment.ui.screens.product.ProductScreen

@Composable
fun AppNavigation(
    startDestination: String = NavScreen.Splash.route
) {
    val navController = rememberNavController()

    NavHost (navController = navController, startDestination = startDestination) {
        composable(NavScreen.Splash.route) {
            SplashScreen(navController)
        }
        composable(NavScreen.Login.route) {
            LoginScreen(navController)
        }
        composable(NavScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(NavScreen.Product.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (productId != null) {
                ProductScreen(
                    navController = navController,
                    productId = productId
                )
            } else {
                ErrorScreen(
                    onNavigateBack = { navController.popBackStack() },
                    message = stringResource(R.string.error_invalid_product_id)
                )
            }
        }
        composable(NavScreen.Cart.route) {
            CartScreen(navController)
        }
        composable(NavScreen.CheckOut.route) {
            CheckOutScreen(navController)
        }
    }
}