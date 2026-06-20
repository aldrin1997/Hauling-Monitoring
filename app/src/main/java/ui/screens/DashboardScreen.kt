package com.asdec.hauling.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.asdec.hauling.data.Batch
import com.asdec.hauling.ui.MainViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    onNavigateToAddTruck: (String) -> Unit,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToAdmin: () -> Unit
) {
    val batches by viewModel.batches.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Local state to track if sync is in progress
    var isSyncing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hauling Dashboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    // --- SYNC BUTTON ---
                    IconButton(
                        onClick = {
                            scope.launch {
                                isSyncing = true
                                try {
                                    viewModel.syncData() // Ensure this is implemented in MainViewModel
                                    Toast.makeText(context, "Cloud Sync Successful!", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Sync Failed: ${e.message}", Toast.LENGTH_LONG).show()
                                } finally {
                                    isSyncing = false
                                }
                            }
                        },
                        enabled = !isSyncing
                    ) {
                        if (isSyncing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Sync,
                                contentDescription = "Sync Cloud",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Admin Icon
                    IconButton(onClick = onNavigateToAdmin) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Management",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Logout Button
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Red
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val newBatchId = UUID.randomUUID().toString()
                    viewModel.clearForm()
                    onNavigateToAddTruck(newBatchId)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                icon = { Icon(Icons.Default.Add, "New Batch") },
                text = { Text("New Truck Entry") }
            )
        }
    ) { padding ->
        if (batches.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.History,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("No hauling records yet", color = Color.Gray)
                Text("Tap '+' to start a new entry", style = MaterialTheme.typography.labelSmall)
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(batches, key = { it.id }) { batch ->
                    BatchItem(
                        batch = batch,
                        onClick = {
                            onNavigateToDetails(batch.id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchItem(batch: Batch, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Batch ID: ${batch.id.take(8).uppercase()}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    // NEW: Show a small cloud icon if synced
                    if (batch.isSynced) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = "Synced",
                                modifier = Modifier.size(14.dp),
                                tint = Color(0xFF2E7D32)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Cloud Synced", style = MaterialTheme.typography.labelSmall, color = Color(0xFF2E7D32))
                        }
                    }
                }

                val statusColor = when(batch.status) {
                    "STAGED" -> Color(0xFF2E7D32)
                    "DRAFT" -> Color(0xFFED6C02)
                    else -> MaterialTheme.colorScheme.outline
                }

                Badge(containerColor = statusColor) {
                    Text(
                        text = batch.status,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)

            val date = try {
                SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault()).format(Date(batch.date))
            } catch (e: Exception) {
                "Unknown Date"
            }

            Text("Date Created: $date", style = MaterialTheme.typography.bodyMedium)

            Text(
                text = "Created By: ${batch.createdBy}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )

            Text("Project: ${batch.projectId}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tap to view details →",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}