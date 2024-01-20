package com.sirin.findartevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirin.findartevent.ui.screens.FindArtEventAppNavigationGraph
import com.sirin.findartevent.ui.screens.Routes
import com.sirin.findartevent.ui.theme.FindArtEventTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindArtEventTheme {
               FindArtEventApp()
            }
        }
    }
}

@Composable
fun FindArtEventApp(){
   FindArtEventAppNavigationGraph()
}

