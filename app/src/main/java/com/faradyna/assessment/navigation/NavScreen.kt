package com.faradyna.assessment.navigation

sealed class NavScreen(val route: String) {
    object Splash : NavScreen("main")
    object Login : NavScreen("login")
    object Home : NavScreen("home")
    object Product: NavScreen("product/{id}") {
        fun createRoute(id: Int) : String {
            return "product/$id"
        }
    }
    object Cart: NavScreen("cart")
    object CheckOut: NavScreen("checkout")
}