package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.Accommodation
import com.example.ui.components.AccommodationCard
import com.example.ui.components.AppCreatorFooter
import com.example.ui.components.FeatureBadge
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.MumbaiViewModel

@Composable
fun HomeScreen(
    viewModel: MumbaiViewModel,
    modifier: Modifier = Modifier
) {
    val stayState by viewModel.stayState.collectAsStateWithLifecycle()
    val savedStays by viewModel.savedStays.collectAsStateWithLifecycle()
    val routeState by viewModel.routeState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Hero Banner with generated artwork
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_mumbai_hero),
                    contentDescription = "Mumbai Skyline and Transit Illustration",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC0B192C), Color(0xFF0F2851))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Aamchi Mumbai",
                        color = Color(0xFFFBBF24),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Newcomer's Transit & Stay Guide",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Navigate local trains, BEST buses & find budget PGs easily",
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Live Transit Status Alert Bar
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Color(0xFF16A34A))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Live Status: Western, Central & Metro Line 3 running normal",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Quick Navigation Tiles
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Quick Services",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        title = "Live Trains",
                        subtitle = "WR, CR, Metro",
                        icon = Icons.Default.Train,
                        color = Color(0xFFE11D48),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.TRANSIT) }
                    )
                    QuickActionTile(
                        title = "Find Stays",
                        subtitle = "PGs, Hostels",
                        icon = Icons.Default.Home,
                        color = Color(0xFF0F2851),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.STAYS) }
                    )
                    QuickActionTile(
                        title = "Plan Route",
                        subtitle = "Maps + Trains",
                        icon = Icons.Default.AltRoute,
                        color = Color(0xFF0284C7),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.ROUTES) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionTile(
                        title = "Cost Matrix",
                        subtitle = "Compare Suburbs",
                        icon = Icons.Default.Payments,
                        color = Color(0xFFD97706),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.COST_MATRIX) }
                    )
                    QuickActionTile(
                        title = "Survival Guide",
                        subtitle = "Etiquette & Hacks",
                        icon = Icons.Default.Lightbulb,
                        color = Color(0xFF0D9488),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.GUIDE) }
                    )
                    QuickActionTile(
                        title = "Emergency",
                        subtitle = "GRP, RPF, 112",
                        icon = Icons.Default.Emergency,
                        color = Color(0xFFDC2626),
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.selectNavTab(AppNavTab.GUIDE) }
                    )
                }
            }
        }

        // Route Planner Quick Jump Box
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🚀 Commute Route Planner",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        FeatureBadge(
                            text = "Google Maps Transit",
                            backgroundColor = Color(0xFF0284C7)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "From ${routeState.origin} ➔ To ${routeState.destination}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                viewModel.calculateRoutes()
                                viewModel.selectNavTab(AppNavTab.ROUTES)
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("home_plan_route_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Navigation, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("View 3 Route Options")
                        }
                    }
                }
            }
        }

        // Newcomer Pro Tip of the Day
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Campaign,
                            contentDescription = null,
                            tint = Color(0xFFB45309)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mumbai Local Golden Rule",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFFB45309)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Always ask 'Utarna hai kya?' before blocking the coach door. People queue up 2 stations early to exit!",
                        fontSize = 12.sp,
                        color = Color(0xFF78350F),
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Featured Affordable PGs Section
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Top Affordable Stays",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(
                    onClick = { viewModel.selectNavTab(AppNavTab.STAYS) },
                    modifier = Modifier.testTag("home_view_all_stays")
                ) {
                    Text("View All (${stayState.accommodations.size})")
                }
            }
        }

        items(stayState.accommodations.take(3)) { stay ->
            val isSaved = savedStays.any { it.id == stay.id }
            AccommodationCard(
                stay = stay,
                isSaved = isSaved,
                onToggleSave = { viewModel.toggleSaveStay(stay) },
                onClick = { viewModel.setSelectedStay(stay) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Creator Footer at the bottom
        item {
            Spacer(modifier = Modifier.height(10.dp))
            AppCreatorFooter(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
private fun QuickActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .clickable { onClick() }
            .testTag("tile_${title.lowercase().replace(" ", "_")}")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
