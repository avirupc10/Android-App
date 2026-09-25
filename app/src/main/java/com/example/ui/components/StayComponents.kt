package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.model.Accommodation
import com.example.data.model.GenderSuitability

@Composable
fun AccommodationCard(
    stay: Accommodation,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("stay_card_${stay.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Title, Area, Bookmark Button
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stay.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (stay.isVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = Color(0xFF0284C7),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Text(
                        text = "📍 ${stay.area}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("save_stay_btn_${stay.id}")
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isSaved) "Remove bookmark" else "Save stay",
                        tint = if (isSaved) Color(0xFFDC2626) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Rent & Deposit Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "₹${stay.monthlyRent}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " / month",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                FeatureBadge(
                    text = "Deposit: ₹${stay.depositAmount} (${stay.depositMonths} mo)",
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Type, Occupancy, Gender Badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FeatureBadge(
                    text = stay.stayType.label,
                    backgroundColor = Color(0xFF0F2851)
                )

                FeatureBadge(
                    text = stay.occupancy,
                    backgroundColor = Color(0xFFD97706)
                )

                if (stay.gender != GenderSuitability.ANY) {
                    FeatureBadge(
                        text = stay.gender.label,
                        backgroundColor = if (stay.gender == GenderSuitability.GIRLS) Color(0xFFBE185D) else Color(0xFF2563EB)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Nearest Station & Distance
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsWalk,
                    contentDescription = "Walk",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${stay.stationDistanceKm} km to ${stay.nearestStation} (${stay.walkTimeToStationMin} min walk)",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Top Amenities
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(stay.amenities.take(4)) { amenity ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "✓ $amenity",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom row: Rating & View Button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "★ ${stay.rating}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD97706)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${stay.reviewCount} reviews)",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("view_stay_details_${stay.id}")
                ) {
                    Text("View & Commute", fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StayDetailBottomSheet(
    stay: Accommodation,
    isSaved: Boolean,
    currentNote: String,
    onSaveToggle: (note: String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var noteText by remember(stay.id, currentNote) { mutableStateOf(currentNote) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stay.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stay.address,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Rent, Deposit, Type Banner
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Monthly Rent", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${stay.monthlyRent}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                    }
                    Divider(modifier = Modifier.height(36.dp).width(1.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Deposit", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("₹${stay.depositAmount}", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                    Divider(modifier = Modifier.height(36.dp).width(1.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Occupancy", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(stay.occupancy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Commute times to corporate/education hubs
            Text(
                text = "🚆 Travel Times to Key Mumbai Hubs",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stay.commuteToHubs.forEach { (hub, mins) ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(hub, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Text("$mins min", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Maps Navigation Action
            Button(
                onClick = {
                    openGoogleMaps(context, stay.latitude, stay.longitude, stay.title)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F2851)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_maps_btn")
            ) {
                Icon(imageVector = Icons.Default.Map, contentDescription = "Map")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Open Location in Google Maps")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Call Owner Button
            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${stay.contactPhone}")
                    }
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("call_owner_btn")
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Call")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Contact ${stay.contactName} (${stay.contactPhone})")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = "About this Stay",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stay.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Amenities List
            Text(
                text = "Included Amenities",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                stay.amenities.forEach { amenity ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(amenity, fontSize = 12.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // House Rules
            Text(
                text = "House Rules & Notice",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            stay.rules.forEach { rule ->
                Text(
                    text = "• $rule",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Local Room DB: Personal Note & Bookmark
            Text(
                text = "📝 Your Personal Notes (Saved Locally in DB)",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                placeholder = { Text("e.g. Visited owner on Sunday, food was tasty, deposit negotiable") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("stay_note_input")
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onSaveToggle(noteText) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSaved) Color(0xFFDC2626) else MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("save_with_note_btn")
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isSaved) "Update / Remove Saved Stay" else "Save Stay & Note to DB")
            }
        }
    }
}

private fun openGoogleMaps(context: Context, lat: Double, lng: Double, label: String) {
    try {
        val geoUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng(${Uri.encode(label)})")
        val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
            context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
        }
    } catch (e: Exception) {
        val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}
