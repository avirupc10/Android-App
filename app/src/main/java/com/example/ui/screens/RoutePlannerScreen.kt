package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.RoutePlan
import com.example.data.model.TransitStep
import com.example.ui.components.FeatureBadge
import com.example.ui.viewmodel.MumbaiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutePlannerScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    val routeState by viewModel.routeState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showSaveDialog by remember { mutableStateOf(false) }
    var commuteLabel by remember { mutableStateOf("Daily Work Commute") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("route_planner_screen")
    ) {
        // Source & Destination Input Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Mumbai Transit Route Planner",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = routeState.origin,
                            onValueChange = { viewModel.updateOrigin(it) },
                            label = { Text("Starting Station / Suburb") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.TripOrigin,
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("origin_input")
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = routeState.destination,
                            onValueChange = { viewModel.updateDestination(it) },
                            label = { Text("Destination / Office / College") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    tint = Color(0xFFDC2626)
                                )
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("dest_input")
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { viewModel.swapRouteEndpoints() },
                        modifier = Modifier
                            .size(42.dp)
                            .testTag("swap_endpoints_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Swap Origin and Destination",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Quick Popular Hub Chips
                Text(
                    text = "Quick Stations:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(viewModel.popularStations) { station ->
                        SuggestionChip(
                            onClick = {
                                viewModel.updateDestination(station)
                                viewModel.calculateRoutes()
                            },
                            label = { Text(station, fontSize = 11.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.calculateRoutes() },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("calculate_routes_btn")
                    ) {
                        Icon(imageVector = Icons.Default.AltRoute, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Find Transit Routes")
                    }

                    OutlinedButton(
                        onClick = { showSaveDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("save_commute_btn")
                    ) {
                        Icon(imageVector = Icons.Default.BookmarkAdd, contentDescription = "Save Commute")
                    }
                }
            }
        }

        // Routes Comparison Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Optimal Multi-Modal Routes (${routeState.plannedRoutes.size})",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Direct Google Maps Transit button
            TextButton(
                onClick = {
                    openGoogleMapsTransit(context, routeState.origin, routeState.destination)
                },
                modifier = Modifier.testTag("open_gmaps_transit_header_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Google Maps Navigation", fontSize = 12.sp)
            }
        }

        // Route Options List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(routeState.plannedRoutes) { plan ->
                RoutePlanCard(
                    plan = plan,
                    onOpenMaps = {
                        openGoogleMapsTransit(context, plan.mapsQueryOrigin, plan.mapsQueryDestination)
                    }
                )
            }

            // Fare Comparison Breakdown Card
            item {
                PriceComparisonSummaryCard(
                    origin = routeState.origin,
                    destination = routeState.destination
                )
            }
        }
    }

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Commute Route") },
            text = {
                Column {
                    Text("Save this regular route for fast 1-tap schedule checking and price comparisons.")
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = commuteLabel,
                        onValueChange = { commuteLabel = it },
                        label = { Text("Commute Name / Tag") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val fastest = routeState.plannedRoutes.firstOrNull()
                        viewModel.saveCommuteRoute(
                            source = routeState.origin,
                            destination = routeState.destination,
                            mode = fastest?.title ?: "Multi-Modal",
                            mins = fastest?.totalTimeMins ?: 40,
                            fare = fastest?.totalFare ?: 35,
                            label = commuteLabel
                        )
                        showSaveDialog = false
                    }
                ) {
                    Text("Save to Favorites")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun RoutePlanCard(
    plan: RoutePlan,
    onOpenMaps: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("route_plan_${plan.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with Tag, Time, and Fare
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    FeatureBadge(
                        text = plan.tag,
                        backgroundColor = when (plan.tag) {
                            "Fastest" -> Color(0xFFDC2626)
                            "Cheapest" -> Color(0xFF16A34A)
                            else -> Color(0xFF0284C7)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = plan.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${plan.totalTimeMins} min",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    FeatureBadge(
                        text = "₹${plan.totalFare}",
                        backgroundColor = Color(0xFF16A34A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Step-by-Step Breakdown
            plan.steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(28.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(step.lineHex?.let { Color(it) } ?: MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (index < plan.steps.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(36.dp)
                                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = step.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "${step.durationMins}m • ~₹${step.fareApprox}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Text(
                            text = step.instruction,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Crowd / Peak Hour Warning
            plan.crowdWarning?.let { warning ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = warning,
                        fontSize = 11.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Google Maps Transit Button
            OutlinedButton(
                onClick = onOpenMaps,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_maps_route_${plan.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsTransit,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Live Route in Google Maps")
            }
        }
    }
}

@Composable
private fun PriceComparisonSummaryCard(origin: String, destination: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "💰 Mode Price Comparison Matrix",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = "Estimated travel cost from $origin to $destination across all modes:",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            ModePriceRow(mode = "Local Train (2nd Class)", time = "25-35 min", fare = "₹5 - ₹10", badge = "Super Saver")
            ModePriceRow(mode = "BEST Ordinary Bus", time = "40-55 min", fare = "₹6 - ₹15", badge = "Low Cost")
            ModePriceRow(mode = "Mumbai Metro (AC)", time = "20-25 min", fare = "₹20 - ₹40", badge = "Fast & Cool")
            ModePriceRow(mode = "Auto Rickshaw (Meter)", time = "35-45 min", fare = "₹110 - ₹160", badge = "Doorstep")
            ModePriceRow(mode = "Cab / Taxi (Uber/Ola)", time = "35-50 min", fare = "₹220 - ₹340", badge = "Private")
        }
    }
}

@Composable
private fun ModePriceRow(mode: String, time: String, fare: String, badge: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(mode, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Text(time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(fare, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        FeatureBadge(
            text = badge,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun openGoogleMapsTransit(context: Context, origin: String, destination: String) {
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
