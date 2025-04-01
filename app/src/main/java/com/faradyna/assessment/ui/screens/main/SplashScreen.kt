package com.faradyna.assessment.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.faradyna.assessment.R
import com.faradyna.assessment.navigation.NavScreen
import com.faradyna.assessment.navigation.events.SplashEvent
import com.faradyna.assessment.ui.components.CustomBackground
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        splashViewModel.navigationEvent.collectLatest { navigationEvent ->
            when (navigationEvent) {
                is SplashEvent.ToHome -> {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(NavScreen.Splash.route) { inclusive = true }
                    }
                }

                else -> {
                    navController.navigate(NavScreen.Login.route) {
                        popUpTo(NavScreen.Splash.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold { paddingValues ->
        CustomBackground(
            modifier = Modifier.padding(paddingValues),
            backgroundColor = Color.Yellow
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.desc_logo),
                modifier = Modifier.size(150.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(
        navController = rememberNavController(),
        splashViewModel = hiltViewModel(),
    )
}