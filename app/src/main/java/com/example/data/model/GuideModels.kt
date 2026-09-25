package com.example.data.model

data class EmergencyContact(
    val title: String,
    val number: String,
    val description: String,
    val category: String // "Railway", "Police", "Women Safety", "City Helpline"
)

data class MumbaiSurvivalTip(
    val id: String,
    val title: String,
    val category: String, // "Local Train Rules", "Ticket Booking", "Buses & Autos", "Renting Hacks"
    val summary: String,
    val fullDetails: String,
    val iconName: String
)

data class FareRuleInfo(
    val mode: String,
    val baseFare: String,
    val baseDistance: String,
    val perKmRate: String,
    val nightSurcharge: String,
    val importantNotice: String
)
