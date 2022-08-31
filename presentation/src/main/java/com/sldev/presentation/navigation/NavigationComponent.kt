package com.sldev.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.sldev.presentation.list_screen.ImagesListScreen

@Composable
fun NavigationComponent() {
    Navigator(ImagesListScreen)
}