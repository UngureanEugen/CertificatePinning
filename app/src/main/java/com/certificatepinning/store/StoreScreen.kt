package com.certificatepinning.store

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.certificatepinning.data.Store

@Composable
fun storeScreen(state: State<UiState>) {
    when (val currentState = state.value) {
        is UiState.Error -> errorView(currentState.exception)
        is UiState.Loading -> loadingView()
        is UiState.Success -> storesView(currentState.stores)
    }
}

@Composable
fun errorView(exception: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Error is $exception",
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun loadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun storesView(stores: List<Store>) {
    LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
        items(stores.size) { index ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(text = stores[index].name, style = MaterialTheme.typography.body1)
                Text(text = stores[index].address, style = MaterialTheme.typography.body2)
                Text(
                    text = "${stores[index].city}, ${stores[index].postalCode}",
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
