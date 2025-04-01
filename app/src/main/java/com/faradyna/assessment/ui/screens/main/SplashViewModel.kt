package com.faradyna.assessment.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradyna.assessment.domain.datastore.DatastoreManager
import com.faradyna.assessment.navigation.events.SplashEvent
import com.faradyna.assessment.utility.qualifiers.DataStoreManagerQualifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @DataStoreManagerQualifier private val dataStore: DatastoreManager,
) : ViewModel() {

    init {
        decideNavigation()
    }

    private val _navigationEvent by lazy { MutableSharedFlow<SplashEvent>() }
    val navigationEvent: SharedFlow<SplashEvent> get() = _navigationEvent

    private fun decideNavigation() {
        viewModelScope.launch {
            val user = dataStore.getUser().firstOrNull()
            _navigationEvent.emit(
                if (user != null) {
                    SplashEvent.ToHome
                } else {
                    SplashEvent.ToLogin
                }
            )
        }
    }
}