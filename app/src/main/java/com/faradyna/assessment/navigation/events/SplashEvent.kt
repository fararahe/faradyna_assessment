package com.faradyna.assessment.navigation.events

sealed class SplashEvent {
    object ToHome : SplashEvent()
    object ToLogin: SplashEvent()
}