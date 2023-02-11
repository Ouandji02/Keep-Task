package com.phone.keeptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.phone.keeptask.ui.navigation.Navigation
import com.phone.keeptask.ui.theme.KeepTaskTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepTaskTheme {
                val lifecycleOwner = LocalLifecycleOwner.current
                val permissionState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.SEND_SMS
                    )
                )
                DisposableEffect(key1 = lifecycleOwner) {
                    val eventObservable = LifecycleEventObserver { _, event ->
                        when (event) {
                            Lifecycle.Event.ON_CREATE -> {
                                permissionState.launchMultiplePermissionRequest()
                            }
                            else -> {}
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(eventObservable)
                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(eventObservable)
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KeepTaskTheme {
        Greeting("Android")
    }
}