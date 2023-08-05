package com.nebka.cryptoeye.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nebka.cryptoeye.ui.theme.CryptoEyeTheme
import com.nebka.cryptoeye.util.Constants.SEARCH_SCREEN
import com.nebka.cryptoeye.util.Constants.SPLASH_SCREEN
import com.nebka.cryptoeye.viewmodels.TagViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val tagViewModel = hiltViewModel<TagViewModel>()

            // displaying error message
            val errorState by tagViewModel.errorState.collectAsStateWithLifecycle()
            if (errorState.isNotEmpty())
                Toast.makeText(LocalContext.current, errorState, Toast.LENGTH_LONG).show()

            CryptoEyeTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(tagViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(viewModel: TagViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        composable(SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }

        composable(SEARCH_SCREEN) {
            SearchScreen(viewModel)
        }
    }
}
