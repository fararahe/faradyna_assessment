package com.faradyna.assessment.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.auth.resp.TokenDomain
import com.faradyna.assessment.navigation.NavScreen
import com.faradyna.assessment.ui.common.HandleErrorStates
import com.faradyna.assessment.ui.components.CustomBackground
import com.faradyna.assessment.ui.components.CustomButton
import com.faradyna.assessment.ui.components.CustomOutlinedTextField
import com.faradyna.assessment.ui.shared_viewmodel.EncryptedPrefViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    encryptedPrefViewModel: EncryptedPrefViewModel = hiltViewModel(),
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val loginState by loginViewModel.resultLogin.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val password by loginViewModel.password.collectAsState()

    HandleLoginSuccess(
        navController = navController,
        loginViewModel = loginViewModel,
        encryptedPrefViewModel = encryptedPrefViewModel,
        loginState = loginState,
    )

    HandleErrorStates(
        navController = navController,
        loginState
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        CustomBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomOutlinedTextField(
                    value = username,
                    onValueChange = { loginViewModel.setUsername(it) },
                    label = stringResource(R.string.label_username),
                    validator = {
                        loginViewModel.validateUsername()
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomOutlinedTextField(
                    value = password,
                    onValueChange = { loginViewModel.setPassword(it) },
                    label = stringResource(R.string.label_password),
                    isPassword = true,
                    validator = {
                        loginViewModel.validatePassword()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(
                    text = stringResource(R.string.label_login),
                    onClick = {
                        keyboardController?.hide()
                        loginViewModel.login(username, password)
                    },
                    isLoading = loginState.isLoading(),
                )
            }
        }
    }
}

@Composable
fun HandleLoginSuccess(
    navController: NavController,
    loginViewModel: LoginViewModel,
    encryptedPrefViewModel: EncryptedPrefViewModel,
    loginState: ResultState<TokenDomain?>,
) {
    LaunchedEffect(loginState) {
        (loginState as? ResultState.Success)?.data?.let { data ->
            encryptedPrefViewModel.saveToken(data.token)
            loginViewModel.checkAndStoreUser()
            navController.navigate(NavScreen.Home.route) {
                popUpTo(NavScreen.Login.route) { inclusive = true }
            }
        }
    }
}