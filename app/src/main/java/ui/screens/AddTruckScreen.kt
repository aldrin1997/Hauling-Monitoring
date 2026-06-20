package com.asdec.hauling.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.asdec.hauling.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTruckScreen(
    batchId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onNavigateToCamera: (String) -> Unit
) {
    var isSaving by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Form data from ViewModel
    val projectName = viewModel.projectName
    val hauler = viewModel.hauler
    val plate = viewModel.plate

    val photoPaths by viewModel.photoPaths.collectAsState()
    val sides = listOf("Front", "Back", "Left", "Right")
    val capturedPhotos = sides.filter { side -> photoPaths.containsKey("${batchId}_$side") }

    // Logic: Time In is recorded when the record is created
    val timeIn = remember {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Truck Arrival", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Step 1: Record Entrance Details", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        TimeCard("Recorded Time In", timeIn, Modifier.fillMaxWidth(), Color(0xFFE8F5E9))

        Spacer(modifier = Modifier.height(16.dp))

        // PROJECT NAME
        OutlinedTextField(
            value = projectName,
            onValueChange = { viewModel.projectName = it },
            label = { Text("Project Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving,
            placeholder = { Text("e.g. Lincoln Cebu City") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = hauler,
            onValueChange = { viewModel.hauler = it },
            label = { Text("Hauler Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving
        )

        OutlinedTextField(
            value = plate,
            onValueChange = { viewModel.plate = it },
            label = { Text("Plate No.") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isSaving
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Entrance Photos (${capturedPhotos.size}/4)", style = MaterialTheme.typography.titleMedium)
        Text("Capture at least the front view before proceeding", style = MaterialTheme.typography.labelSmall, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.height(240.dp)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(sides.size) { index ->
                    val side = sides[index]
                    PhotoCard(
                        label = side,
                        imagePath = photoPaths["${batchId}_$side"],
                        onClick = { if (!isSaving) onNavigateToCamera(side) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ACTIONS: Simultaneous Entry Logic
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // OPTION 1: Log this truck and stay to log the next truck immediately
            OutlinedButton(
                onClick = {
                    isSaving = true
                    viewModel.startTruckEntry(
                        batchId = batchId,
                        timeIn = timeIn
                    ) {
                        isSaving = false
                        // resetForNextTruck is called inside startTruckEntry in ViewModel
                    }
                },
                modifier = Modifier.weight(1f).height(56.dp),
                enabled = capturedPhotos.isNotEmpty() && plate.isNotBlank() && !isSaving
            ) {
                Text("Log & Add Next")
            }

            // OPTION 2: Log this truck and go back to see Active list
            Button(
                onClick = {
                    isSaving = true
                    viewModel.startTruckEntry(
                        batchId = batchId,
                        timeIn = timeIn
                    ) {
                        onBack()
                    }
                },
                modifier = Modifier.weight(1f).height(56.dp),
                enabled = capturedPhotos.isNotEmpty() && plate.isNotBlank() && !isSaving
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Confirm Arrival", fontWeight = FontWeight.Bold)
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFFE65100))
                Spacer(Modifier.width(8.dp))
                Text(
                    "Note: Dimensions (L,W,H) will be recorded upon truck EXIT.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color(0xFFE65100)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

// --- SUB-COMPONENTS (RETAINED) ---

@Composable
fun PhotoCard(label: String, imagePath: String?, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = if (imagePath != null) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth().height(110.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (imagePath != null) {
                AsyncImage(
                    model = imagePath,
                    contentDescription = label,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                )
            } else {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = null)
                    Text(text = label, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun TimeCard(label: String, time: String, modifier: Modifier, bgColor: Color) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = bgColor)) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(label, style = MaterialTheme.typography.labelSmall)
            Text(time, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}