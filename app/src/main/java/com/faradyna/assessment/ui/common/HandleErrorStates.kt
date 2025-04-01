package com.faradyna.assessment.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.faradyna.assessment.core.configs.exception.ErrorApiStateHandler
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.data.provider.ErrorHandlerProviderImpl
import com.faradyna.assessment.navigation.NavScreen
import com.faradyna.assessment.ui.screens.LocalTokenManager
import com.faradyna.assessment.utility.extensions.toastShortExt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Composable
fun HandleErrorStates(
    navController: NavController,
    vararg states: ResultState<*>,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val tokenManager = LocalTokenManager.current
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    states.forEach { state ->
        (state as? ResultState.Error)?.exception?.let { throwable ->
            ErrorApiStateHandler.handleErrorState(
                throwable = throwable,
                onHandleableException = { exception ->
                    context.toastShortExt(exception.message)
                },
                onGeneralException = {
                    ErrorHandlerProviderImpl().generalError(context, lifecycleOwner, throwable)
                },
                onTokenExpired = {
                    mainScope.launch {
                        tokenManager.logout()
                        navController.navigate(NavScreen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
            )
        }
    }
}