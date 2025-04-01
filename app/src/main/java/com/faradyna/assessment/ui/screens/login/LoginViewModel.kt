package com.faradyna.assessment.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradyna.assessment.R
import com.faradyna.assessment.core.configs.state.ResultState
import com.faradyna.assessment.domain.auth.request.LoginRequest
import com.faradyna.assessment.domain.auth.resp.TokenDomain
import com.faradyna.assessment.domain.auth.usecase.LoginUseCase
import com.faradyna.assessment.domain.datastore.DatastoreManager
import com.faradyna.assessment.domain.room.user.RoomUserRepository
import com.faradyna.assessment.domain.user.usecase.FetchUsersUsecase
import com.faradyna.assessment.utility.qualifiers.DataStoreManagerQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    @DataStoreManagerQualifier private val dataStore: DatastoreManager,
    private val roomUserRepository: RoomUserRepository,
    private val fetchUsersUsecase: FetchUsersUsecase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            val localUsers = roomUserRepository.getUsersCount()
            if (localUsers > 0) {
                return@launch
            }

            fetchUsersUsecase.execute(FetchUsersUsecase.RequestValues()).collect {
                if (it.result is ResultState.Success && it.result.data != null) {
                    roomUserRepository.storeUsers(it.result.data)
                }
            }
        }
    }

    private val _username by lazy { MutableStateFlow("") }
    val username: StateFlow<String> get() = _username.asStateFlow()

    fun setUsername(username: String) {
        _username.value = username
    }

    private val _password by lazy { MutableStateFlow("") }
    val password: StateFlow<String> get() = _password.asStateFlow()

    fun setPassword(password: String) {
        _password.value = password
    }

    fun validateUsername(): String? {
        val username = username.value
        if (username.isBlank()) return context.getString(R.string.warn_username_cannot_be_empty)
        if (username.length < 3) return context.getString(R.string.warn_must_be_at_least_3_characters)
        if (username.contains(" ")) return context.getString(R.string.warn_cannot_contain_spaces)
        return null
    }

    fun validatePassword(): String? {
        val userInput = password.value
        if (userInput.isBlank()) return context.getString(R.string.warn_password_cannot_be_empty)
        if (userInput.length < 6) return context.getString(R.string.warn_must_be_at_least_6_characters)
        if (!userInput.any { it.isLetter() } || !userInput.any { it.isDigit() }) {
            return context.getString(R.string.warn_must_contain_at_least_one_letter_and_one_number)
        }
        if (userInput.contains(" ")) return context.getString(R.string.warn_cannot_contain_spaces)
        return null
    }


    private val _resultLogin by lazy { MutableStateFlow<ResultState<TokenDomain?>>(ResultState.Idle) }
    val resultLogin: StateFlow<ResultState<TokenDomain?>> get() = _resultLogin.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _resultLogin.value = ResultState.Loading
            _username.value = username
            val request = LoginRequest(
                username = username,
                password = password,
            )
            loginUseCase.execute(LoginUseCase.RequestValues(request)).collect {
                _resultLogin.value = it.result
            }
        }
    }

    fun checkAndStoreUser() {
        viewModelScope.launch {
            val user = roomUserRepository.getUserByUsername(username.value)
            if (user != null) {
                dataStore.storeUser(user)
            }
        }
    }
}