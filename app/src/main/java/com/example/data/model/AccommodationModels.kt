package com.example.data.model

enum class StayType(val label: String) {
    ALL("All Types"),
    PG_WITH_FOOD("PG (Food Included)"),
    PG_NO_FOOD("PG (Self Cook/Mess)"),
    SHARED_FLAT("Flatmate / Shared Flat"),
    HOSTEL("Hostel / Co-Living"),
    BUDGET_1RK("Budget 1RK / Studio")
}

enum class GenderSuitability(val label: String) {
    ANY("Any / Unisex"),
    BOYS("Boys Only"),
    GIRLS("Girls Only")
}

data class Accommodation(
    val id: String,
    val title: String,
    val area: String,
    val address: String,
    val stayType: StayType,
    val gender: GenderSuitability,
    val monthlyRent: Int,
    val depositAmount: Int,
    val depositMonths: Int,
    val occupancy: String, // "Single Room", "Twin Sharing", "3 Sharing", "Private 1RK"
    val nearestStation: String,
    val stationDistanceKm: Double,
    val walkTimeToStationMin: Int,
    val nearestMetro: String? = null,
    val commuteToHubs: Map<String, Int>, // e.g. "BKC" -> 25 mins, "Lower Parel" -> 35 mins
    val amenities: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val isVerified: Boolean = true,
    val hasNoBrokerage: Boolean = true,
    val contactName: String,
    val contactPhone: String,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val rules: List<String>
)

data class SuburbCostIndex(
    val areaName: String,
    val zone: String, // "Western Suburbs", "Central Suburbs", "Harbour / Navi Mumbai", "South Mumbai"
    val avgRentPGSharing: Int,
    val avgRent1BHK: Int,
    val avgMonthlyTransitPass: Int,
    val avgDailyAutoExpense: Int,
    val totalEstimatedMonthlyLiving: Int,
    val commuteConnectivityScore: Int, // 1 to 10
    val bestFor: String,
    val keyStations: List<String>
)
