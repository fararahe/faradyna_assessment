package com.faradyna.assessment.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.faradyna.assessment.core.configs.networking.TokenManager
import com.faradyna.assessment.navigation.AppNavigation
import com.faradyna.assessment.ui.theme.AssessmentTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

val LocalTokenManager = compositionLocalOf<TokenManager> {
    error("TokenProvider not provided")
}

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalTokenManager provides tokenManager
            ) {
                AssessmentTheme {
                    AppNavigation()
                }
            }
        }
    }
}