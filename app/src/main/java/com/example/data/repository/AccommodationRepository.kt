package com.example.data.repository

import com.example.data.model.Accommodation
import com.example.data.model.GenderSuitability
import com.example.data.model.StayType
import com.example.data.model.SuburbCostIndex

class AccommodationRepository {

    private val accommodations = listOf(
        Accommodation(
            id = "stay_01",
            title = "Aamchi Co-Living (Near Andheri Station)",
            area = "Andheri East",
            address = "Plot 42, Near Chakala Metro & Andheri Rly Station East, Andheri (E), Mumbai 400069",
            stayType = StayType.PG_WITH_FOOD,
            gender = GenderSuitability.ANY,
            monthlyRent = 12500,
            depositAmount = 15000,
            depositMonths = 1,
            occupancy = "Twin Sharing",
            nearestStation = "Andheri (WR / Metro Line 1)",
            stationDistanceKm = 0.8,
            walkTimeToStationMin = 9,
            nearestMetro = "Chakala Metro Station (4 mins walk)",
            commuteToHubs = mapOf("BKC" to 28, "SEEPZ" to 12, "Lower Parel" to 38, "Powai" to 22),
            amenities = listOf("WiFi", "AC", "3 Meals Daily", "Washing Machine", "Daily Maid", "Power Backup", "CCTV Security"),
            rating = 4.6,
            reviewCount = 84,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Ramesh Kadam (Manager)",
            contactPhone = "+919820112345",
            latitude = 19.1197,
            longitude = 72.8464,
            description = "Ideal for IT and media professionals in Andheri/SEEPZ. High-speed 200 Mbps Wi-Fi, home-style North & South Indian meals included, biometric access, and 9-min walking distance to both Railway and Metro lines.",
            rules = listOf("Gate closes at 11:30 PM (Biometric)", "Visitors allowed till 8 PM in lounge", "Non-smoking property")
        ),
        Accommodation(
            id = "stay_02",
            title = "Sai Kripa Executive PG for Women",
            area = "Ghatkopar West",
            address = "Shreyas Colony, LBS Marg, Ghatkopar (W), Mumbai 400086",
            stayType = StayType.PG_WITH_FOOD,
            gender = GenderSuitability.GIRLS,
            monthlyRent = 9500,
            depositAmount = 12000,
            depositMonths = 1,
            occupancy = "Triple Sharing",
            nearestStation = "Ghatkopar (CR & Metro Line 1)",
            stationDistanceKm = 0.6,
            walkTimeToStationMin = 7,
            nearestMetro = "Ghatkopar Metro (7 mins walk)",
            commuteToHubs = mapOf("BKC" to 22, "Andheri SEEPZ" to 20, "Lower Parel" to 32, "Thane" to 20),
            amenities = listOf("WiFi", "Breakfast & Dinner", "AC", "Washing Machine", "RO Water", "24/7 Female Warden", "CCTV"),
            rating = 4.8,
            reviewCount = 112,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Sunita Tai",
            contactPhone = "+919833445566",
            latitude = 19.0860,
            longitude = 72.9090,
            description = "Safe and homely accommodation for working women and students. Central location with direct access to Ghatkopar Central local and Metro Line 1 to Andheri. Nutritious home-cooked food.",
            rules = listOf("Female visitors only in room", "Notice period 30 days", "Entry allowed till 11:00 PM")
        ),
        Accommodation(
            id = "stay_03",
            title = "Hiranandani Edge Flatmate Co-Living",
            area = "Powai",
            address = "Central Avenue, Hiranandani Gardens, Powai, Mumbai 400076",
            stayType = StayType.SHARED_FLAT,
            gender = GenderSuitability.ANY,
            monthlyRent = 17000,
            depositAmount = 34000,
            depositMonths = 2,
            occupancy = "Private Bedroom in 3BHK",
            nearestStation = "Kanjurmarg / Vikhroli (CR)",
            stationDistanceKm = 3.2,
            walkTimeToStationMin = 25,
            nearestMetro = "Powai JVLR Metro (under test)",
            commuteToHubs = mapOf("Powai IIT" to 5, "BKC" to 35, "SEEPZ" to 18, "Andheri" to 25),
            amenities = listOf("High-Speed WiFi", "AC", "Attached Bath", "Fully Equipped Kitchen", "Washing Machine", "Housekeeping", "Gym Access"),
            rating = 4.7,
            reviewCount = 65,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Nikhil Sharma",
            contactPhone = "+919769001122",
            latitude = 19.1197,
            longitude = 72.9051,
            description = "Premium private room in a lush Hiranandani 3BHK high-rise. Walking distance to Galleria shopping, cafes, and major tech parks (Kensington, Supreme Business Park).",
            rules = listOf("Chilled out working professionals", "Pet friendly", "Smoking only in balcony")
        ),
        Accommodation(
            id = "stay_04",
            title = "Budget Scholar Boys Hostel & PG",
            area = "Kurla / BKC Connector",
            address = "Near Phoenix Marketcity, LBS Road, Kurla (W), Mumbai 400070",
            stayType = StayType.PG_NO_FOOD,
            gender = GenderSuitability.BOYS,
            monthlyRent = 6500,
            depositAmount = 8000,
            depositMonths = 1,
            occupancy = "3 & 4 Sharing",
            nearestStation = "Kurla Station (CR & Harbour Line)",
            stationDistanceKm = 0.7,
            walkTimeToStationMin = 8,
            nearestMetro = "BKC Metro Line 3 (1.5 km)",
            commuteToHubs = mapOf("BKC" to 15, "Lower Parel" to 25, "CSMT" to 30, "Andheri" to 30),
            amenities = listOf("WiFi", "RO Filter Water", "Locker Storage", "Common Geyser", "Maid Service"),
            rating = 4.2,
            reviewCount = 93,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Anwar Bhai",
            contactPhone = "+919920334411",
            latitude = 19.0726,
            longitude = 72.8845,
            description = "Super-affordable base for freshers starting out in Mumbai. Direct ₹30 share auto to BKC corporate hub, excellent dual-line local train connectivity (Central & Harbour).",
            rules = listOf("No alcohol/drugs on premises", "Lockers provided for valuables", "Notice period 15 days")
        ),
        Accommodation(
            id = "stay_05",
            title = "Green Oasis Co-Living Studio & 1RK",
            area = "Thane West",
            address = "Naupada, Gokhale Road, Thane (W), Mumbai MMR 400602",
            stayType = StayType.BUDGET_1RK,
            gender = GenderSuitability.ANY,
            monthlyRent = 8000,
            depositAmount = 15000,
            depositMonths = 2,
            occupancy = "Private 1RK Apartment",
            nearestStation = "Thane Station (CR & Trans-Harbour)",
            stationDistanceKm = 1.1,
            walkTimeToStationMin = 13,
            nearestMetro = "Thane Metro Ring Line",
            commuteToHubs = mapOf("BKC" to 42, "CSMT" to 48, "Dadar" to 35, "Airoli Mindspace" to 18),
            amenities = listOf("WiFi", "Separate Kitchenette", "Private Washroom", "24/7 Municipal Water", "Balcony"),
            rating = 4.5,
            reviewCount = 76,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Mahesh Patil",
            contactPhone = "+919819887766",
            latitude = 19.1860,
            longitude = 72.9759,
            description = "Independent studio living at half the South/West Mumbai rent. Thane provides peaceful residential streets, Naupada food lane, and fast local trains originating directly from Thane station.",
            rules = listOf("Direct owner deal (No brokerage)", "Quiet residential building", "Family/bachelors welcome")
        ),
        Accommodation(
            id = "stay_06",
            title = "Mindspace Techies Haven PG",
            area = "Malad West",
            address = "Behind Inorbit Mall, Link Road, Malad (W), Mumbai 400064",
            stayType = StayType.PG_WITH_FOOD,
            gender = GenderSuitability.ANY,
            monthlyRent = 11000,
            depositAmount = 15000,
            depositMonths = 1,
            occupancy = "Twin Sharing",
            nearestStation = "Malad (WR) & Malad Metro (Yellow Line)",
            stationDistanceKm = 1.4,
            walkTimeToStationMin = 16,
            nearestMetro = "Lower Malad Metro (5 mins walk)",
            commuteToHubs = mapOf("Mindspace" to 6, "Andheri" to 18, "BKC" to 45, "Borivali" to 12),
            amenities = listOf("WiFi", "AC", "Veg/Non-Veg Food", "Washing Machine", "Fridge", "Maid Service", "CCTV"),
            rating = 4.4,
            reviewCount = 59,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Vikram Singh",
            contactPhone = "+919867554433",
            latitude = 19.1874,
            longitude = 72.8397,
            description = "5-minute walk to Mindspace IT Park and Lower Malad Metro station. Includes fresh home-style meals twice a day, high speed Wi-Fi, and spacious rooms with individual wooden wardrobes.",
            rules = listOf("Visitors allowed in day hours", "Quiet hours after 11 PM", "Rent due by 5th")
        ),
        Accommodation(
            id = "stay_07",
            title = "Vashi Sea-Breeze Executive Co-Living",
            area = "Vashi, Navi Mumbai",
            address = "Sector 17, Near Vashi Plaza, Navi Mumbai 400703",
            stayType = StayType.HOSTEL,
            gender = GenderSuitability.ANY,
            monthlyRent = 7500,
            depositAmount = 10000,
            depositMonths = 1,
            occupancy = "Twin / Triple Sharing",
            nearestStation = "Vashi Railway Station (Harbour & Trans)",
            stationDistanceKm = 1.0,
            walkTimeToStationMin = 12,
            commuteToHubs = mapOf("CSMT" to 45, "BKC" to 38, "Thane" to 24, "Belapur" to 16),
            amenities = listOf("WiFi", "AC", "Gaming / Chill Lounge", "Washing Machine", "Hot Water", "Microwave", "Housekeeping"),
            rating = 4.6,
            reviewCount = 88,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Kunal Varma",
            contactPhone = "+919821443322",
            latitude = 19.0771,
            longitude = 72.9986,
            description = "Wide tree-lined avenues, clean planned city roads, and ultra-affordable rent. Directly connected to CSMT via Harbour local trains and Dadar via C-42 Express BEST bus.",
            rules = listOf("Coliving community events on weekends", "No smoking in room", "Notice period 30 days")
        ),
        Accommodation(
            id = "stay_08",
            title = "Bandra Station Bayview Room",
            area = "Bandra East",
            address = "Kalanagar, Near BKC Flyover, Bandra (E), Mumbai 400051",
            stayType = StayType.SHARED_FLAT,
            gender = GenderSuitability.ANY,
            monthlyRent = 21000,
            depositAmount = 40000,
            depositMonths = 2,
            occupancy = "Private Bedroom in 2BHK",
            nearestStation = "Bandra Station (WR & HR)",
            stationDistanceKm = 0.5,
            walkTimeToStationMin = 6,
            nearestMetro = "Bandra Colony / BKC Metro 3",
            commuteToHubs = mapOf("BKC" to 8, "Lower Parel" to 18, "Andheri" to 15, "CSMT" to 28),
            amenities = listOf("High-Speed WiFi", "AC", "Attached Bath", "Modular Kitchen", "Maid & Cook", "Balcony View"),
            rating = 4.9,
            reviewCount = 42,
            isVerified = true,
            hasNoBrokerage = true,
            contactName = "Siddharth Roy",
            contactPhone = "+919820556677",
            latitude = 19.0596,
            longitude = 72.8499,
            description = "Walking distance to BKC financial offices! Located right next to Kalanagar, avoiding all peak-hour BKC traffic jams. Ideal for young consultants, bankers, and lawyers.",
            rules = listOf("Working professionals preferred", "Quiet and neat housemate needed")
        )
    )

    private val suburbCostIndices = listOf(
        SuburbCostIndex(
            areaName = "Thane West",
            zone = "Central Suburbs",
            avgRentPGSharing = 7500,
            avgRent1BHK = 16000,
            avgMonthlyTransitPass = 350,
            avgDailyAutoExpense = 1200,
            totalEstimatedMonthlyLiving = 13500,
            commuteConnectivityScore = 9,
            bestFor = "Freshers, IT professionals, family living",
            keyStations = listOf("Thane Railway Station (Fast Local origin)")
        ),
        SuburbCostIndex(
            areaName = "Andheri East",
            zone = "Western Suburbs",
            avgRentPGSharing = 13500,
            avgRent1BHK = 28000,
            avgMonthlyTransitPass = 260,
            avgDailyAutoExpense = 1800,
            totalEstimatedMonthlyLiving = 21500,
            commuteConnectivityScore = 10,
            bestFor = "Media, Aviation, Tech (SEEPZ & MIDC)",
            keyStations = listOf("Andheri WR", "Metro Line 1", "Metro Line 7")
        ),
        SuburbCostIndex(
            areaName = "Ghatkopar West",
            zone = "Central Suburbs",
            avgRentPGSharing = 9500,
            avgRent1BHK = 22000,
            avgMonthlyTransitPass = 280,
            avgDailyAutoExpense = 1400,
            totalEstimatedMonthlyLiving = 16500,
            commuteConnectivityScore = 10,
            bestFor = "Commuters needing both Central & Western connectivity",
            keyStations = listOf("Ghatkopar CR", "Metro Line 1 (Versova Connect)")
        ),
        SuburbCostIndex(
            areaName = "Vashi, Navi Mumbai",
            zone = "Navi Mumbai",
            avgRentPGSharing = 7200,
            avgRent1BHK = 15000,
            avgMonthlyTransitPass = 380,
            avgDailyAutoExpense = 1000,
            totalEstimatedMonthlyLiving = 12800,
            commuteConnectivityScore = 8,
            bestFor = "Peaceful planned locality, clean air, budget saving",
            keyStations = listOf("Vashi Harbour Line", "Trans-Harbour to Thane")
        ),
        SuburbCostIndex(
            areaName = "Kurla / BKC",
            zone = "Central / Island Border",
            avgRentPGSharing = 8500,
            avgRent1BHK = 20000,
            avgMonthlyTransitPass = 220,
            avgDailyAutoExpense = 1500,
            totalEstimatedMonthlyLiving = 15500,
            commuteConnectivityScore = 9,
            bestFor = "Walking/short auto to BKC offices on a budget",
            keyStations = listOf("Kurla Junction (CR & HR)", "BKC Metro 3")
        ),
        SuburbCostIndex(
            areaName = "Powai",
            zone = "Central Western Hub",
            avgRentPGSharing = 16000,
            avgRent1BHK = 32000,
            avgMonthlyTransitPass = 450,
            avgDailyAutoExpense = 2400,
            totalEstimatedMonthlyLiving = 24500,
            commuteConnectivityScore = 7,
            bestFor = "Tech startups, IIT campus life, luxury township",
            keyStations = listOf("Kanjurmarg CR", "JVLR Metro link")
        ),
        SuburbCostIndex(
            areaName = "Malad & Kandivali",
            zone = "Western Suburbs",
            avgRentPGSharing = 9500,
            avgRent1BHK = 21000,
            avgMonthlyTransitPass = 300,
            avgDailyAutoExpense = 1500,
            totalEstimatedMonthlyLiving = 16200,
            commuteConnectivityScore = 9,
            bestFor = "Mindspace IT workers, bachelors, retail lovers",
            keyStations = listOf("Malad WR", "Metro Yellow Line 2A")
        ),
        SuburbCostIndex(
            areaName = "Bandra West / East",
            zone = "Western Suburbs",
            avgRentPGSharing = 22000,
            avgRent1BHK = 45000,
            avgMonthlyTransitPass = 200,
            avgDailyAutoExpense = 2000,
            totalEstimatedMonthlyLiving = 32000,
            commuteConnectivityScore = 10,
            bestFor = "Lifestyle, sea promenade, high-end corporate proximity",
            keyStations = listOf("Bandra WR / HR", "Bandra Terminus")
        )
    )

    fun getAllAccommodations(): List<Accommodation> = accommodations

    fun getAccommodationById(id: String): Accommodation? =
        accommodations.find { it.id == id }

    fun filterAccommodations(
        maxRent: Int?,
        area: String?,
        stayType: StayType,
        gender: GenderSuitability,
        maxStationDistanceKm: Double?
    ): List<Accommodation> {
        return accommodations.filter { item ->
            val matchesRent = maxRent == null || item.monthlyRent <= maxRent
            val matchesArea = area.isNullOrBlank() || area == "All Areas" || item.area.contains(area, ignoreCase = true)
            val matchesType = stayType == StayType.ALL || item.stayType == stayType
            val matchesGender = gender == GenderSuitability.ANY || item.gender == GenderSuitability.ANY || item.gender == gender
            val matchesDist = maxStationDistanceKm == null || item.stationDistanceKm <= maxStationDistanceKm
            matchesRent && matchesArea && matchesType && matchesGender && matchesDist
        }
    }

    fun getAllAreas(): List<String> = listOf(
        "All Areas", "Andheri East", "Ghatkopar West", "Powai",
        "Kurla / BKC", "Thane West", "Malad West", "Vashi, Navi Mumbai", "Bandra East"
    )

    fun getSuburbCostIndices(): List<SuburbCostIndex> = suburbCostIndices
}
