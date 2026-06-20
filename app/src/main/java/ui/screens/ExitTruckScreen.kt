package com.asdec.hauling.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.asdec.hauling.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExitTruckScreen(
    truckId: Long,
    batchId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Truck Exit / Dimensions") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Recording Exit for Truck ID: $truckId", style = MaterialTheme.typography.labelLarge)

            OutlinedTextField(
                value = viewModel.L,
                onValueChange = { viewModel.L = it },
                label = { Text("Length (L)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.W,
                onValueChange = { viewModel.W = it },
                label = { Text("Width (W)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = viewModel.H,
                onValueChange = { viewModel.H = it },
                label = { Text("Height (H)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.finishTruckEntry(truckId, batchId, currentTime) {
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text("FINISH & CALCULATE VOLUME")
            }
        }
    }
}