package com.asdec.hauling.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.asdec.hauling.data.Truck
import com.asdec.hauling.ui.MainViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TruckDetailsScreen(
    batchId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onNavigateToExit: (Long) -> Unit
) {
    val trucks by viewModel.selectedTrucks.collectAsState()
    val inProgressTrucks by viewModel.inProgressTrucks.collectAsState()
    val photoPaths by viewModel.photoPaths.collectAsState()

    var truckToDelete by remember { mutableStateOf<Truck?>(null) }

    LaunchedEffect(batchId) {
        viewModel.loadTruckDetails(batchId)
        viewModel.loadInProgressTrucks(batchId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Batch: $batchId") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- SECTION 1: HEADER ---
            item {
                Text(
                    text = "Project: ${viewModel.projectName}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Summary: ${trucks.size} Completed, ${inProgressTrucks.size} On-Site",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // --- SECTION 2: ACTIVE TRUCKS (Waiting for Exit) ---
            if (inProgressTrucks.isNotEmpty()) {
                item {
                    Text(
                        "TRUCKS ON-SITE (ACTIVE)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFE65100)
                    )
                }
                items(inProgressTrucks) { truck ->
                    ActiveTruckCard(
                        truck = truck,
                        onExitClick = { onNavigateToExit(truck.id) }
                    )
                }
            }

            // --- SECTION 3: COMPLETED HISTORY ---
            item {
                Text(
                    "DELIVERY HISTORY",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (trucks.isEmpty() && inProgressTrucks.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No records found.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            } else {
                items(trucks) { truck ->
                    // Only show completed trucks in the history list
                    if (truck.status == "COMPLETED") {
                        TruckInfoCard(
                            truck = truck,
                            photoPaths = photoPaths,
                            onDeleteClick = { truckToDelete = truck }
                        )
                    }
                }
            }
        }
    }

    // Confirmation Dialog for Truck Deletion
    if (truckToDelete != null) {
        AlertDialog(
            onDismissRequest = { truckToDelete = null },
            title = { Text("Delete Truck Record") },
            text = { Text("Are you sure you want to delete truck ${truckToDelete?.plateNo}? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        truckToDelete?.let { viewModel.deleteTruck(it.id, batchId) }
                        truckToDelete = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { truckToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * Special Card for trucks that have arrived but NOT yet exited.
 */
@Composable
fun ActiveTruckCard(truck: Truck, onExitClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExitClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(truck.plateNo, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Hauler: ${truck.haulerName}", style = MaterialTheme.typography.bodySmall)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Login, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color(0xFF2E7D32))
                    Spacer(Modifier.width(4.dp))
                    Text("Arrived at ${truck.timeIn}", style = MaterialTheme.typography.labelSmall)
                }
            }
            Button(
                onClick = onExitClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("EXIT")
            }
        }
    }
}

@Composable
fun TruckInfoCard(
    truck: Truck,
    photoPaths: Map<String, String>,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalShipping, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = truck.plateNo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Hauler: ${truck.haulerName}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DetailColumn("L", "${truck.length}m")
                DetailColumn("W", "${truck.width}m")
                DetailColumn("H", "${truck.height}m")
                DetailColumn("Volume", "${String.format("%.2f", truck.cum)} m³", isPrimary = true)
            }

            Spacer(Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("In: ${truck.timeIn}", style = MaterialTheme.typography.labelSmall)
                Text("Out: ${truck.timeOut}", style = MaterialTheme.typography.labelSmall)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val types = listOf("Front", "Back", "Left", "Right")
                types.forEach { type ->
                    val path = photoPaths["${truck.id}_$type"]
                    PhotoPreview(path = path, label = type)
                }
            }
        }
    }
}

// Sub-components (DetailColumn, PhotoPreview) remain unchanged from your original code.
@Composable
fun DetailColumn(label: String, value: String, isPrimary: Boolean = false) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
        Text(
            text = value,
            style = if (isPrimary) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
fun PhotoPreview(path: String?, label: String) {
    var showZoom by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier.size(75.dp),
            onClick = { if (path != null) showZoom = true },
            shape = MaterialTheme.shapes.small
        ) {
            if (!path.isNullOrEmpty()) {
                AsyncImage(
                    model = File(path),
                    contentDescription = label,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No $label", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }

    if (showZoom && path != null) {
        Dialog(onDismissRequest = { showZoom = false }) {
            Surface(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(model = File(path), contentDescription = null, modifier = Modifier.fillMaxWidth().aspectRatio(1f), contentScale = ContentScale.Fit)
                    TextButton(onClick = { showZoom = false }) { Text("Close") }
                }
            }
        }
    }
}