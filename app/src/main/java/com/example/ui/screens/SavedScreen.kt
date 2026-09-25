package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.SavedCommuteEntity
import com.example.data.local.SavedStayEntity
import com.example.ui.components.FeatureBadge
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.MumbaiViewModel

@Composable
fun SavedScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    val savedStays by viewModel.savedStays.collectAsStateWithLifecycle()
    val savedCommutes by viewModel.savedCommutes.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("saved_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Saved Commutes Header
        item {
            Text(
                text = "📌 Saved Commute Routes (${savedCommutes.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (savedCommutes.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No saved routes yet. Use 'Plan Route' and tap bookmark to save regular office/college journeys!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(savedCommutes) { commute ->
                SavedCommuteCard(
                    commute = commute,
                    onOpenPlanner = {
                        viewModel.updateOrigin(commute.source)
                        viewModel.updateDestination(commute.destination)
                        viewModel.calculateRoutes()
                        viewModel.selectNavTab(AppNavTab.ROUTES)
                    },
                    onOpenMaps = {
                        openMapsTransit(context, commute.source, commute.destination)
                    },
                    onDelete = { viewModel.deleteCommute(commute.id) }
                )
            }
        }

        // Saved Stays Header
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "🏠 Bookmarked PGs & Stays (${savedStays.size})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (savedStays.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No saved stays yet. Browse 'Find Stay' and tap the heart icon on any PG or hostel.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(savedStays) { stay ->
                SavedStayItemCard(
                    stay = stay,
                    onCall = { dialNumber(context, stay.contactPhone) },
                    onDelete = { viewModel.removeSavedStay(stay.id) },
                    onUpdateNote = { newNote -> viewModel.updateStayNote(stay.id, newNote) }
                )
            }
        }
    }
}

@Composable
private fun SavedCommuteCard(
    commute: SavedCommuteEntity,
    onOpenPlanner: () -> Unit,
    onOpenMaps: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = commute.label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${commute.source} ➔ ${commute.destination}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FeatureBadge(text = commute.preferredMode, backgroundColor = Color(0xFF0F2851))
                FeatureBadge(text = "~${commute.estTimeMins} mins", backgroundColor = Color(0xFFD97706))
                FeatureBadge(text = "₹${commute.estFare}", backgroundColor = Color(0xFF16A34A))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onOpenPlanner,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Check Live Timetable", fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = onOpenMaps,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Google Maps", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun SavedStayItemCard(
    stay: SavedStayEntity,
    onCall: () -> Unit,
    onDelete: () -> Unit,
    onUpdateNote: (String) -> Unit
) {
    var note by remember(stay.userNote) { mutableStateOf(stay.userNote) }
    var isEditingNote by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stay.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "📍 ${stay.area} • Near ${stay.nearestStation}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹${stay.monthlyRent} / mo",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = onCall,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call Owner", fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // User note from Room DB
            if (isEditingNote) {
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    placeholder = { Text("Add personal note (e.g. visited owner, deposit terms)") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { isEditingNote = false }) { Text("Cancel") }
                    Button(onClick = {
                        onUpdateNote(note)
                        isEditingNote = false
                    }) { Text("Save Note") }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (note.isNotBlank()) "📝 $note" else "📝 No note added yet",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { isEditingNote = true }) {
                        Text(if (note.isNotBlank()) "Edit Note" else "Add Note", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

private fun dialNumber(context: Context, number: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$number")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        // Ignore
    }
}

private fun openMapsTransit(context: Context, origin: String, destination: String) {
    try {
        val encOrigin = Uri.encode("$origin, Mumbai")
        val encDest = Uri.encode("$destination, Mumbai")
        val mapsUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$encOrigin&destination=$encDest&travelmode=transit")
        val intent = Intent(Intent.ACTION_VIEW, mapsUri)
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            context.startActivity(Intent(Intent.ACTION_VIEW, mapsUri))
        }
    } catch (e: Exception) {
        val encOrigin = Uri.encode("$origin, Mumbai")
        val encDest = Uri.encode("$destination, Mumbai")
        val webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=$encOrigin&destination=$encDest&travelmode=transit")
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}
