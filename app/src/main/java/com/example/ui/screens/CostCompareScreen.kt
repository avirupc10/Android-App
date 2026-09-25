package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SuburbCostIndex
import com.example.ui.components.FeatureBadge
import com.example.ui.viewmodel.MumbaiViewModel

@Composable
fun CostCompareScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    var userBudget by remember { mutableStateOf(20000f) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("cost_compare_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header & explanation
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🏙️ Mumbai Suburb Cost of Living Comparison",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Newcomers often make the mistake of renting close to office without factoring transit pass savings. Compare total monthly expenses (Rent + Local Train Pass + Daily Transit) across Mumbai suburbs.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Interactive Monthly Budget Filter Slider
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Your Target Monthly Living Budget",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "₹${userBudget.toInt()}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Slider(
                        value = userBudget,
                        onValueChange = { userBudget = it },
                        valueRange = 10000f..40000f,
                        steps = 5,
                        modifier = Modifier.testTag("cost_slider")
                    )

                    Text(
                        text = "Suburbs matching budget: ${viewModel.suburbCostIndices.count { it.totalEstimatedMonthlyLiving <= userBudget }} of ${viewModel.suburbCostIndices.size}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Text(
                text = "Suburb Breakdown (Sorted by Affordability)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(viewModel.suburbCostIndices.sortedBy { it.totalEstimatedMonthlyLiving }) { suburb ->
            val isWithinBudget = suburb.totalEstimatedMonthlyLiving <= userBudget
            SuburbCostCard(suburb = suburb, isWithinBudget = isWithinBudget)
        }
    }
}

@Composable
private fun SuburbCostCard(suburb: SuburbCostIndex, isWithinBudget: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isWithinBudget) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            1.dp,
            if (isWithinBudget) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("suburb_card_${suburb.areaName.lowercase().replace(" ", "_")}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Area name, Zone, Connectivity Score
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = suburb.areaName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        if (isWithinBudget) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Within budget",
                                tint = Color(0xFF16A34A),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Text(
                        text = suburb.zone,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Connectivity",
                        tint = Color(0xFFD97706),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${suburb.commuteConnectivityScore}/10 Transit",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Cost Grid: PG rent, 1BHK, Train pass, Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Avg PG (Sharing)", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("₹${suburb.avgRentPGSharing}/mo", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Avg 1BHK", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("₹${suburb.avgRent1BHK}/mo", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text("Train Pass", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("₹${suburb.avgMonthlyTransitPass}/mo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Est. Living Cost", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        text = "₹${suburb.totalEstimatedMonthlyLiving}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isWithinBudget) MaterialTheme.colorScheme.primary else Color(0xFFDC2626)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Best For Tag
            Text(
                text = "💡 Best For: ${suburb.bestFor}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Key Stations
            Text(
                text = "🚆 Hubs: ${suburb.keyStations.joinToString(", ")}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
