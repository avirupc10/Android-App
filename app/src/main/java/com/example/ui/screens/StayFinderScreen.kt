package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.GenderSuitability
import com.example.data.model.StayType
import com.example.ui.components.AccommodationCard
import com.example.ui.components.SearchInputField
import com.example.ui.components.StayDetailBottomSheet
import com.example.ui.viewmodel.MumbaiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StayFinderScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    val stayState by viewModel.stayState.collectAsStateWithLifecycle()
    val savedStays by viewModel.savedStays.collectAsStateWithLifecycle()
    var showFilterSheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("stay_finder_screen")
    ) {
        // Search & Filter header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchInputField(
                    value = stayState.searchQuery,
                    onValueChange = { viewModel.setStaySearch(it) },
                    placeholder = "Search PG, area, nearest station...",
                    modifier = Modifier.weight(1f),
                    testTag = "stay_search_input"
                )

                FilledTonalIconButton(
                    onClick = { showFilterSheet = true },
                    modifier = Modifier.testTag("open_filters_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter options"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Area Quick Filter Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.availableAreas) { area ->
                    FilterChip(
                        selected = stayState.selectedArea == area,
                        onClick = { viewModel.setStayArea(area) },
                        label = { Text(area, fontSize = 12.sp) },
                        modifier = Modifier.testTag("area_chip_${area.lowercase().replace(" ", "_")}")
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Stay Type Chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(StayType.values()) { type ->
                    FilterChip(
                        selected = stayState.selectedType == type,
                        onClick = { viewModel.setStayType(type) },
                        label = { Text(type.label, fontSize = 11.sp) },
                        modifier = Modifier.testTag("type_chip_${type.name.lowercase()}")
                    )
                }
            }
        }

        Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

        // Results Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(
                text = "${stayState.accommodations.size} verified stays found",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Max Budget: ₹${stayState.maxBudget}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Accommodation Cards List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (stayState.accommodations.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No accommodations match your filter criteria.",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                viewModel.setStayArea("All Areas")
                                viewModel.setStayType(StayType.ALL)
                                viewModel.setStayGender(GenderSuitability.ANY)
                                viewModel.setMaxBudget(30000)
                            }
                        ) {
                            Text("Reset Filters")
                        }
                    }
                }
            } else {
                items(stayState.accommodations) { stay ->
                    val isSaved = savedStays.any { it.id == stay.id }
                    AccommodationCard(
                        stay = stay,
                        isSaved = isSaved,
                        onToggleSave = { viewModel.toggleSaveStay(stay) },
                        onClick = { viewModel.setSelectedStay(stay) }
                    )
                }
            }
        }
    }

    // Detail Bottom Sheet
    stayState.selectedStay?.let { stay ->
        val savedEntity = savedStays.find { it.id == stay.id }
        val isSaved = savedEntity != null
        StayDetailBottomSheet(
            stay = stay,
            isSaved = isSaved,
            currentNote = savedEntity?.userNote ?: "",
            onSaveToggle = { note ->
                viewModel.toggleSaveStay(stay, note)
            },
            onDismiss = { viewModel.setSelectedStay(null) }
        )
    }

    // Filter Bottom Sheet for budget and gender
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Filter Accommodations",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Max Monthly Budget: ₹${stayState.maxBudget}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Slider(
                    value = stayState.maxBudget.toFloat(),
                    onValueChange = { viewModel.setMaxBudget(it.toInt()) },
                    valueRange = 5000f..35000f,
                    steps = 5,
                    modifier = Modifier.testTag("budget_slider")
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Gender Preference",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GenderSuitability.values().forEach { gender ->
                        FilterChip(
                            selected = stayState.selectedGender == gender,
                            onClick = { viewModel.setStayGender(gender) },
                            label = { Text(gender.label, fontSize = 12.sp) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showFilterSheet = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("apply_filters_btn")
                ) {
                    Text("Apply Filters (${stayState.accommodations.size} Results)")
                }
            }
        }
    }
}
