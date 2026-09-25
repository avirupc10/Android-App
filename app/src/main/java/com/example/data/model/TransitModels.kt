package com.example.data.model

enum class TransitType(val displayName: String) {
    ALL("All Transit"),
    LOCAL_TRAIN("Local Train"),
    BEST_BUS("BEST Bus"),
    METRO("Metro"),
    AUTO_TAXI("Auto & Taxi")
}

enum class TrainLine(val lineName: String, val code: String, val colorHex: Long) {
    WESTERN("Western Line", "WR", 0xFFE11D48), // Churchgate - Dahanu
    CENTRAL("Central Line", "CR", 0xFFD97706), // CSMT - Kalyan/Karjat/Kasara
    HARBOUR("Harbour Line", "HR", 0xFF2563EB), // CSMT - Panvel
    TRANS_HARBOUR("Trans-Harbour", "TH", 0xFF0D9488), // Thane - Vashi/Panvel
    METRO_1("Metro Line 1 (Blue)", "M1", 0xFF0284C7), // Versova - Andheri - Ghatkopar
    METRO_2A_7("Metro 2A & 7 (Yellow/Red)", "M2", 0xFFEAB308), // Dahisar - Gundavali/Andheri W
    METRO_3("Metro Line 3 (Aqua)", "M3", 0xFF06B6D4) // Aarey - BKC - Cuffe Parade
}

enum class CrowdLevel(val label: String, val colorHex: Long) {
    SEATS_AVAILABLE("Light Crowd", 0xFF16A34A),
    MODERATE("Moderate Crowd", 0xFFD97706),
    PACKED("Heavy Rush", 0xFFEA580C),
    SUPER_DENSE("Super Dense Crush", 0xFFDC2626)
}

data class TrainSchedule(
    val id: String,
    val trainNumber: String,
    val origin: String,
    val destination: String,
    val line: TrainLine,
    val departureTime: String,
    val arrivalTime: String,
    val isFast: Boolean,
    val isAC: Boolean,
    val isLadiesSpecial: Boolean,
    val cars: Int = 12, // 12-Car or 15-Car
    val platform: String,
    val crowdLevel: CrowdLevel,
    val status: String, // "On Time", "Delayed 2m", "Departing"
    val stopsSummary: String,
    val intermediateStops: List<String>
)

data class BusRoute(
    val id: String,
    val routeNumber: String,
    val origin: String,
    val destination: String,
    val via: String,
    val frequencyMinutes: Int,
    val firstBus: String,
    val lastBus: String,
    val fareRegular: Int,
    val fareAC: Int,
    val isAC: Boolean,
    val stopsCount: Int,
    val keyStops: List<String>
)

data class ShareStand(
    val id: String,
    val station: String,
    val destination: String,
    val vehicleType: String, // "Auto", "Kaali-Peeli Taxi"
    val farePerSeat: Int,
    val standLocation: String, // e.g. "East exit near Platform 1"
    val avgWaitMins: Int,
    val tips: String
)

data class TransitStep(
    val mode: TransitType,
    val title: String,
    val instruction: String,
    val durationMins: Int,
    val fareApprox: Int,
    val lineOrRoute: String? = null,
    val lineHex: Long? = null
)

data class RoutePlan(
    val id: String,
    val title: String,
    val tag: String, // "Fastest", "Cheapest", "Most Comfortable (AC)"
    val totalTimeMins: Int,
    val totalFare: Int,
    val steps: List<TransitStep>,
    val crowdWarning: String? = null,
    val mapsQueryOrigin: String,
    val mapsQueryDestination: String
)
