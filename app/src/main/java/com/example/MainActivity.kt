package com.example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppNavTab
import com.example.ui.viewmodel.MumbaiViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MumbaiNewcomerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MumbaiNewcomerApp(
    viewModel: MumbaiViewModel = viewModel()
) {
    val currentTab by viewModel.currentNavTab.collectAsStateWithLifecycle()
    val savedStays by viewModel.savedStays.collectAsStateWithLifecycle()
    val savedCommutes by viewModel.savedCommutes.collectAsStateWithLifecycle()
    val totalSavedCount = savedStays.size + savedCommutes.size
    val context = LocalContext.current

    // Handle back button on secondary screens
    if (currentTab != AppNavTab.HOME) {
        BackHandler {
            viewModel.selectNavTab(AppNavTab.HOME)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Aamchi Mumbai",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = when (currentTab) {
                                AppNavTab.HOME -> "Newcomer Transit & Stay Guide"
                                AppNavTab.TRANSIT -> "Real-Time Train & Bus Schedules"
                                AppNavTab.STAYS -> "Affordable PGs, Hostels & Flats"
                                AppNavTab.ROUTES -> "Multi-Modal Google Maps Transit"
                                AppNavTab.COST_MATRIX -> "Suburb Living Cost Comparison"
                                AppNavTab.GUIDE -> "Local Etiquette & Helplines"
                                AppNavTab.SAVED -> "Your Saved Places & Commutes"
                            },
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    // Railway Police quick SOS dial button
                    IconButton(
                        onClick = { dialRailwayHelpline(context) },
                        modifier = Modifier.testTag("top_bar_sos_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = "Railway Police 1512",
                            tint = Color(0xFFDC2626)
                        )
                    }

                    // Saved Stays Badge
                    IconButton(
                        onClick = { viewModel.selectNavTab(AppNavTab.SAVED) },
                        modifier = Modifier.testTag("top_bar_saved_btn")
                    ) {
                        BadgedBox(
                            badge = {
                                if (totalSavedCount > 0) {
                                    Badge { Text("$totalSavedCount") }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (currentTab == AppNavTab.SAVED) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = "Saved places"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("bottom_nav_bar")
            ) {
                NavigationBarItem(
                    selected = currentTab == AppNavTab.HOME,
                    onClick = { viewModel.selectNavTab(AppNavTab.HOME) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.HOME) Icons.Default.Home else Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_home")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.TRANSIT,
                    onClick = { viewModel.selectNavTab(AppNavTab.TRANSIT) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.TRANSIT) Icons.Default.Train else Icons.Outlined.Train,
                            contentDescription = "Transit"
                        )
                    },
                    label = { Text("Transit", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_transit")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.STAYS,
                    onClick = { viewModel.selectNavTab(AppNavTab.STAYS) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.STAYS) Icons.Default.Apartment else Icons.Outlined.Apartment,
                            contentDescription = "Stays"
                        )
                    },
                    label = { Text("Find Stay", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_stays")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.ROUTES,
                    onClick = { viewModel.selectNavTab(AppNavTab.ROUTES) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.ROUTES) Icons.Default.AltRoute else Icons.Outlined.AltRoute,
                            contentDescription = "Routes"
                        )
                    },
                    label = { Text("Routes", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_routes")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.COST_MATRIX,
                    onClick = { viewModel.selectNavTab(AppNavTab.COST_MATRIX) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.COST_MATRIX) Icons.Default.Payments else Icons.Outlined.Payments,
                            contentDescription = "Cost Matrix"
                        )
                    },
                    label = { Text("Costs", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_costs")
                )

                NavigationBarItem(
                    selected = currentTab == AppNavTab.GUIDE,
                    onClick = { viewModel.selectNavTab(AppNavTab.GUIDE) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == AppNavTab.GUIDE) Icons.Default.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Guide"
                        )
                    },
                    label = { Text("Guide", fontSize = 11.sp) },
                    modifier = Modifier.testTag("nav_item_guide")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppNavTab.HOME -> HomeScreen(viewModel = viewModel)
                AppNavTab.TRANSIT -> TransitScreen(viewModel = viewModel)
                AppNavTab.STAYS -> StayFinderScreen(viewModel = viewModel)
                AppNavTab.ROUTES -> RoutePlannerScreen(viewModel = viewModel)
                AppNavTab.COST_MATRIX -> CostCompareScreen(viewModel = viewModel)
                AppNavTab.GUIDE -> GuideScreen(viewModel = viewModel)
                AppNavTab.SAVED -> SavedScreen(viewModel = viewModel)
            }
        }
    }
}

private fun dialRailwayHelpline(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:1512")
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        // Ignore
    }
}
