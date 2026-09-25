package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.TrainLine
import com.example.data.model.TransitType
import com.example.ui.components.*
import com.example.ui.viewmodel.MumbaiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    val transitState by viewModel.transitState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("transit_screen")
    ) {
        // Top Search & Filter Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SearchInputField(
                value = transitState.searchQuery,
                onValueChange = { viewModel.setTransitSearch(it) },
                placeholder = "Search station, train number or bus route...",
                testTag = "transit_search_input"
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Transit Type Primary Tabs
            ScrollableTabRow(
                selectedTabIndex = transitState.selectedTab.ordinal,
                edgePadding = 0.dp,
                divider = {},
                containerColor = Color.Transparent
            ) {
                TransitType.values().forEach { type ->
                    Tab(
                        selected = transitState.selectedTab == type,
                        onClick = { viewModel.setTransitTab(type) },
                        text = {
                            Text(
                                text = type.displayName,
                                fontSize = 13.sp,
                                fontWeight = if (transitState.selectedTab == type) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.testTag("transit_tab_${type.name.lowercase()}")
                    )
                }
            }
        }

        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        // Sub-filters for Train Lines / Fast & AC toggles
        if (transitState.selectedTab == TransitType.LOCAL_TRAIN || transitState.selectedTab == TransitType.METRO) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Line Selection Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val applicableLines = if (transitState.selectedTab == TransitType.LOCAL_TRAIN) {
                        listOf(TrainLine.WESTERN, TrainLine.CENTRAL, TrainLine.HARBOUR, TrainLine.TRANS_HARBOUR)
                    } else {
                        listOf(TrainLine.METRO_1, TrainLine.METRO_2A_7, TrainLine.METRO_3)
                    }

                    items(applicableLines) { line ->
                        FilterChip(
                            selected = transitState.selectedTrainLine == line,
                            onClick = { viewModel.setTrainLine(line) },
                            label = { Text(line.lineName, fontSize = 12.sp) },
                            leadingIcon = {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(line.colorHex))
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(line.colorHex).copy(alpha = 0.15f),
                                selectedLabelColor = Color(line.colorHex)
                            ),
                            modifier = Modifier.testTag("line_chip_${line.code.lowercase()}")
                        )
                    }
                }

                if (transitState.selectedTab == TransitType.LOCAL_TRAIN) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = transitState.isFastOnly,
                            onClick = { viewModel.toggleFastFilter() },
                            label = { Text("⚡ Fast Trains", fontSize = 11.sp) },
                            modifier = Modifier.testTag("fast_filter_chip")
                        )
                        FilterChip(
                            selected = transitState.isACFilterOnly,
                            onClick = { viewModel.toggleACFilter() },
                            label = { Text("❄️ AC Locals", fontSize = 11.sp) },
                            modifier = Modifier.testTag("ac_filter_chip")
                        )
                    }
                }
            }
        }

        // Content Lists based on active Tab
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when (transitState.selectedTab) {
                TransitType.LOCAL_TRAIN, TransitType.METRO -> {
                    if (transitState.trains.isEmpty()) {
                        item {
                            EmptyStateNotice(
                                title = "No trains found",
                                subtitle = "Try adjusting your search query or filters."
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = "Upcoming Departures (${transitState.trains.size})",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        items(transitState.trains) { train ->
                            TrainScheduleCard(schedule = train)
                        }
                    }
                }

                TransitType.BEST_BUS -> {
                    if (transitState.buses.isEmpty()) {
                        item {
                            EmptyStateNotice(
                                title = "No buses found",
                                subtitle = "Try searching by route number, origin or destination."
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = "Key Commuter Bus Routes (${transitState.buses.size})",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        items(transitState.buses) { bus ->
                            BusRouteCard(bus = bus)
                        }
                    }
                }

                TransitType.AUTO_TAXI -> {
                    item {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color(0xFFB45309)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Suburban Autos: ₹23 min fare (by meter). Share Auto stands operate with fixed regulated rate per seat.",
                                    fontSize = 12.sp,
                                    color = Color(0xFF78350F)
                                )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Station Share Auto & Taxi Stands",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(transitState.shareStands) { stand ->
                        ShareStandCard(stand = stand)
                    }
                }

                TransitType.ALL -> {
                    item {
                        Text(
                            text = "Active Trains & Metros",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    items(transitState.trains.take(4)) { train ->
                        TrainScheduleCard(schedule = train)
                    }

                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "BEST Buses",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    items(transitState.buses.take(3)) { bus ->
                        BusRouteCard(bus = bus)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateNotice(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
