package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MumbaiTransportRepository {

    private val trainSchedules = listOf(
        // Western Line
        TrainSchedule(
            id = "wr_01",
            trainNumber = "BO90422",
            origin = "Churchgate",
            destination = "Borivali",
            line = TrainLine.WESTERN,
            departureTime = "08:14 AM",
            arrivalTime = "09:02 AM",
            isFast = true,
            isAC = false,
            isLadiesSpecial = false,
            cars = 15,
            platform = "PF 3",
            crowdLevel = CrowdLevel.SUPER_DENSE,
            status = "On Time",
            stopsSummary = "Fast: Mumbai Central, Dadar, Bandra, Andheri, Borivali",
            intermediateStops = listOf("Churchgate", "Mumbai Central", "Dadar", "Bandra", "Andheri", "Borivali")
        ),
        TrainSchedule(
            id = "wr_02",
            trainNumber = "AC90112",
            origin = "Churchgate",
            destination = "Virar",
            line = TrainLine.WESTERN,
            departureTime = "08:26 AM",
            arrivalTime = "09:40 AM",
            isFast = true,
            isAC = true,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 4",
            crowdLevel = CrowdLevel.MODERATE,
            status = "Departing",
            stopsSummary = "AC Fast: Dadar, Bandra, Andheri, Borivali, Bhayandar, Vasai, Virar",
            intermediateStops = listOf("Churchgate", "Dadar", "Bandra", "Andheri", "Borivali", "Bhayandar", "Vasai Rd", "Virar")
        ),
        TrainSchedule(
            id = "wr_03",
            trainNumber = "LS90230",
            origin = "Churchgate",
            destination = "Bhayandar",
            line = TrainLine.WESTERN,
            departureTime = "08:35 AM",
            arrivalTime = "09:42 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = true,
            cars = 12,
            platform = "PF 2",
            crowdLevel = CrowdLevel.MODERATE,
            status = "On Time",
            stopsSummary = "Ladies Special Slow: Halts at all stations",
            intermediateStops = listOf("Churchgate", "Marine Lines", "Charni Rd", "Grant Rd", "Mumbai Central", "Dadar", "Bandra", "Andheri", "Borivali", "Bhayandar")
        ),
        TrainSchedule(
            id = "wr_04",
            trainNumber = "AN90510",
            origin = "Churchgate",
            destination = "Andheri",
            line = TrainLine.WESTERN,
            departureTime = "08:44 AM",
            arrivalTime = "09:22 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 1",
            crowdLevel = CrowdLevel.PACKED,
            status = "Delayed 2m",
            stopsSummary = "Slow: All stations up to Andheri",
            intermediateStops = listOf("Churchgate", "Dadar", "Matunga Rd", "Mahim", "Bandra", "Khar", "Santacruz", "Vile Parle", "Andheri")
        ),
        TrainSchedule(
            id = "wr_05",
            trainNumber = "CG90800",
            origin = "Borivali",
            destination = "Churchgate",
            line = TrainLine.WESTERN,
            departureTime = "08:50 AM",
            arrivalTime = "09:36 AM",
            isFast = true,
            isAC = false,
            isLadiesSpecial = false,
            cars = 15,
            platform = "PF 5",
            crowdLevel = CrowdLevel.SUPER_DENSE,
            status = "On Time",
            stopsSummary = "Fast: Andheri, Bandra, Dadar, Mumbai Central, Churchgate",
            intermediateStops = listOf("Borivali", "Andheri", "Bandra", "Dadar", "Mumbai Central", "Churchgate")
        ),

        // Central Line
        TrainSchedule(
            id = "cr_01",
            trainNumber = "KY95311",
            origin = "CSMT",
            destination = "Kalyan",
            line = TrainLine.CENTRAL,
            departureTime = "08:18 AM",
            arrivalTime = "09:22 AM",
            isFast = true,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 5",
            crowdLevel = CrowdLevel.SUPER_DENSE,
            status = "On Time",
            stopsSummary = "Fast: Byculla, Dadar, Kurla, Ghatkopar, Thane, Dombivli, Kalyan",
            intermediateStops = listOf("CSMT", "Byculla", "Dadar", "Kurla", "Ghatkopar", "Thane", "Dombivli", "Kalyan")
        ),
        TrainSchedule(
            id = "cr_02",
            trainNumber = "TH95100",
            origin = "CSMT",
            destination = "Thane",
            line = TrainLine.CENTRAL,
            departureTime = "08:30 AM",
            arrivalTime = "09:26 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 3",
            crowdLevel = CrowdLevel.PACKED,
            status = "Departing",
            stopsSummary = "Slow: All stations to Thane",
            intermediateStops = listOf("CSMT", "Masjid", "Sandhurst Rd", "Byculla", "Chinchpokli", "Currey Rd", "Parel", "Dadar", "Matunga", "Sion", "Kurla", "Ghatkopar", "Vikhroli", "Kanjurmarg", "Bhandup", "Nahur", "Mulund", "Thane")
        ),
        TrainSchedule(
            id = "cr_03",
            trainNumber = "AC95800",
            origin = "CSMT",
            destination = "Dombivli",
            line = TrainLine.CENTRAL,
            departureTime = "08:42 AM",
            arrivalTime = "09:48 AM",
            isFast = true,
            isAC = true,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 7",
            crowdLevel = CrowdLevel.MODERATE,
            status = "On Time",
            stopsSummary = "AC Fast: Byculla, Dadar, Kurla, Ghatkopar, Bhandup, Thane, Dombivli",
            intermediateStops = listOf("CSMT", "Byculla", "Dadar", "Kurla", "Ghatkopar", "Bhandup", "Thane", "Dombivli")
        ),
        TrainSchedule(
            id = "cr_04",
            trainNumber = "KS95640",
            origin = "CSMT",
            destination = "Kasara",
            line = TrainLine.CENTRAL,
            departureTime = "08:58 AM",
            arrivalTime = "11:15 AM",
            isFast = true,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 6",
            crowdLevel = CrowdLevel.PACKED,
            status = "On Time",
            stopsSummary = "Fast: Dadar, Kurla, Ghatkopar, Thane, Kalyan, Titwala, Asangaon, Kasara",
            intermediateStops = listOf("CSMT", "Dadar", "Kurla", "Ghatkopar", "Thane", "Kalyan", "Titwala", "Asangaon", "Kasara")
        ),

        // Harbour Line
        TrainSchedule(
            id = "hr_01",
            trainNumber = "PL98101",
            origin = "CSMT",
            destination = "Panvel",
            line = TrainLine.HARBOUR,
            departureTime = "08:20 AM",
            arrivalTime = "09:40 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 1",
            crowdLevel = CrowdLevel.PACKED,
            status = "On Time",
            stopsSummary = "Slow: Wadala, Kurla, Chembur, Mankhurd, Vashi, Nerul, Belapur, Panvel",
            intermediateStops = listOf("CSMT", "Wadala Rd", "Kurla", "Chembur", "Govandi", "Mankhurd", "Vashi", "Sanpada", "Juinagar", "Nerul", "Seawoods", "Belapur", "Kharghar", "Mansarovar", "Khandeshwar", "Panvel")
        ),
        TrainSchedule(
            id = "hr_02",
            trainNumber = "BR98205",
            origin = "CSMT",
            destination = "Bandra",
            line = TrainLine.HARBOUR,
            departureTime = "08:38 AM",
            arrivalTime = "09:12 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 2",
            crowdLevel = CrowdLevel.MODERATE,
            status = "Departing",
            stopsSummary = "Slow via Wadala Rd & King's Circle to Bandra",
            intermediateStops = listOf("CSMT", "Sandhurst Rd", "Cotton Green", "Sewri", "Wadala Rd", "King's Circle", "Mahim", "Bandra")
        ),
        TrainSchedule(
            id = "hr_03",
            trainNumber = "VA98315",
            origin = "CSMT",
            destination = "Vashi",
            line = TrainLine.HARBOUR,
            departureTime = "08:48 AM",
            arrivalTime = "09:37 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = true,
            cars = 12,
            platform = "PF 1",
            crowdLevel = CrowdLevel.SEATS_AVAILABLE,
            status = "On Time",
            stopsSummary = "Ladies Special: Wadala, Kurla, Mankhurd, Vashi",
            intermediateStops = listOf("CSMT", "Wadala Rd", "Kurla", "Chembur", "Mankhurd", "Vashi")
        ),

        // Trans-Harbour
        TrainSchedule(
            id = "th_01",
            trainNumber = "TH99001",
            origin = "Thane",
            destination = "Panvel",
            line = TrainLine.TRANS_HARBOUR,
            departureTime = "08:25 AM",
            arrivalTime = "09:18 AM",
            isFast = false,
            isAC = false,
            isLadiesSpecial = false,
            cars = 12,
            platform = "PF 9",
            crowdLevel = CrowdLevel.MODERATE,
            status = "On Time",
            stopsSummary = "All halts: Airoli, Rabale, Ghansoli, Koparkhairane, Turbhe, Vashi, Nerul, Panvel",
            intermediateStops = listOf("Thane", "Digha Gaon", "Airoli", "Rabale", "Ghansoli", "Koparkhairane", "Turbhe", "Juinagar", "Nerul", "Belapur", "Panvel")
        ),

        // Metro 1
        TrainSchedule(
            id = "m1_01",
            trainNumber = "METRO-1",
            origin = "Versova",
            destination = "Ghatkopar",
            line = TrainLine.METRO_1,
            departureTime = "Every 4 mins",
            arrivalTime = "21 mins journey",
            isFast = false,
            isAC = true,
            isLadiesSpecial = false,
            cars = 4,
            platform = "Elevated Concourse",
            crowdLevel = CrowdLevel.PACKED,
            status = "Running Every 4m",
            stopsSummary = "Connects Western Line (Andheri) to Central Line (Ghatkopar)",
            intermediateStops = listOf("Versova", "DN Nagar", "Azad Nagar", "Andheri (WR Interchange)", "WEH", "Chakala", "Airport Rd", "Marol Naka", "Saki Naka", "Asalpha", "Jagruti Nagar", "Ghatkopar (CR Interchange)")
        ),

        // Metro 2A & 7
        TrainSchedule(
            id = "m2_01",
            trainNumber = "RED-LINE-7",
            origin = "Gundavali (Andheri E)",
            destination = "Ovaripada (Dahisar)",
            line = TrainLine.METRO_2A_7,
            departureTime = "Every 6 mins",
            arrivalTime = "34 mins journey",
            isFast = false,
            isAC = true,
            isLadiesSpecial = false,
            cars = 6,
            platform = "Platform 1",
            crowdLevel = CrowdLevel.MODERATE,
            status = "Running Every 6m",
            stopsSummary = "Runs parallel to Western Express Highway (WEH)",
            intermediateStops = listOf("Gundavali", "Mogra", "Jogeshwari E", "Goregaon E", "Aarey", "Dindoshi", "Kurar", "Poisar", "Borivali E", "Dahisar E")
        ),

        // Metro 3 Aqua Line
        TrainSchedule(
            id = "m3_01",
            trainNumber = "AQUA-LINE-3",
            origin = "Aarey JVLR",
            destination = "BKC (Bandra Kurla Complex)",
            line = TrainLine.METRO_3,
            departureTime = "Every 7 mins",
            arrivalTime = "22 mins journey",
            isFast = false,
            isAC = true,
            isLadiesSpecial = false,
            cars = 8,
            platform = "Underground Concourse",
            crowdLevel = CrowdLevel.SEATS_AVAILABLE,
            status = "Operating Smoothly",
            stopsSummary = "Underground: Direct connect from SEEPZ & Airport T1/T2 to BKC Financial Hub",
            intermediateStops = listOf("Aarey JVLR", "SEEPZ", "MIDC Andheri", "Marol Naka", "CSMIA T2 Airport", "Sahar Rd", "CSMIA T1 Domestic Airport", "Santacruz", "Bandra Colony", "BKC")
        )
    )

    private val busRoutes = listOf(
        BusRoute(
            id = "bus_a115",
            routeNumber = "A-115 (AC)",
            origin = "CSMT Station",
            destination = "NCPA / Nariman Point",
            via = "Flora Fountain, Hutatma Chowk, Churchgate Station, Air India",
            frequencyMinutes = 10,
            firstBus = "06:30 AM",
            lastBus = "10:30 PM",
            fareRegular = 6,
            fareAC = 6,
            isAC = true,
            stopsCount = 11,
            keyStops = listOf("CSMT", "Flora Fountain", "Churchgate Stn", "Mantralaya", "NCPA")
        ),
        BusRoute(
            id = "bus_332",
            routeNumber = "332",
            origin = "Andheri Station (East)",
            destination = "Kurla Station (West)",
            via = "Pump House, Chakala, Sakinaka, Kamani, Phoenix Marketcity",
            frequencyMinutes = 8,
            firstBus = "05:15 AM",
            lastBus = "11:45 PM",
            fareRegular = 10,
            fareAC = 15,
            isAC = false,
            stopsCount = 24,
            keyStops = listOf("Andheri Stn (E)", "Chakala", "Sakinaka", "Kamani", "Kurla Stn (W)")
        ),
        BusRoute(
            id = "bus_302",
            routeNumber = "302 (AC)",
            origin = "Sion Station",
            destination = "Mulund Check Naka",
            via = "Priyadarshini, Kurla Kamani, Ghatkopar, Vikhroli, Bhandup",
            frequencyMinutes = 12,
            firstBus = "05:45 AM",
            lastBus = "11:00 PM",
            fareRegular = 15,
            fareAC = 20,
            isAC = true,
            stopsCount = 36,
            keyStops = listOf("Sion", "Kurla", "Ghatkopar", "Vikhroli", "Bhandup", "Mulund")
        ),
        BusRoute(
            id = "bus_203",
            routeNumber = "203",
            origin = "Andheri Station (West)",
            destination = "Dahisar Bridge",
            via = "SV Road, Juhu Lane, Jogeshwari, Goregaon, Malad, Kandivali, Borivali",
            frequencyMinutes = 10,
            firstBus = "05:00 AM",
            lastBus = "11:30 PM",
            fareRegular = 15,
            fareAC = 22,
            isAC = false,
            stopsCount = 42,
            keyStops = listOf("Andheri (W)", "Jogeshwari", "Goregaon", "Malad", "Kandivali", "Borivali")
        ),
        BusRoute(
            id = "bus_c42",
            routeNumber = "C-42 Express",
            origin = "Dadar Plaza",
            destination = "Vashi Bus Depot (Navi Mumbai)",
            via = "Eastern Express Highway, Sion, Mankhurd, Vashi Toll Naka",
            frequencyMinutes = 15,
            firstBus = "06:00 AM",
            lastBus = "10:30 PM",
            fareRegular = 25,
            fareAC = 35,
            isAC = true,
            stopsCount = 18,
            keyStops = listOf("Dadar Plaza", "Sion", "Mankhurd", "Vashi Plaza", "Vashi Depot")
        ),
        BusRoute(
            id = "bus_as501",
            routeNumber = "AS-501 (AC)",
            origin = "Kurla Bus Depot",
            destination = "Airoli Bus Station",
            via = "BKC, Chunabhatti, Eastern Express Highway, Mulund Airoli Bridge",
            frequencyMinutes = 20,
            firstBus = "06:30 AM",
            lastBus = "09:30 PM",
            fareRegular = 30,
            fareAC = 40,
            isAC = true,
            stopsCount = 28,
            keyStops = listOf("Kurla Depot", "BKC Connector", "Ghatkopar", "Kanjurmarg", "Airoli")
        )
    )

    private val shareStands = listOf(
        ShareStand(
            id = "ss_01",
            station = "Kurla Station (West)",
            destination = "BKC (Bandra Kurla Complex)",
            vehicleType = "Auto",
            farePerSeat = 30,
            standLocation = "West side exit, right after Foot Over Bridge",
            avgWaitMins = 3,
            tips = "Line moves fast during 8:30-10:30 AM. Keep exact change (₹30) ready!"
        ),
        ShareStand(
            id = "ss_02",
            station = "Bandra Station (East)",
            destination = "BKC / Bharat Diamond Bourse",
            vehicleType = "Auto",
            farePerSeat = 35,
            standLocation = "East exit near auto queue gate",
            avgWaitMins = 4,
            tips = "Authorized stand with queue marshals. Drops at ICICI tower, MCA, and Diamond Bourse."
        ),
        ShareStand(
            id = "ss_03",
            station = "Andheri Station (East)",
            destination = "SEEPZ / MIDC Gate 1",
            vehicleType = "Auto",
            farePerSeat = 25,
            standLocation = "Opposite railway ticket counter east plaza",
            avgWaitMins = 2,
            tips = "High frequency in morning shift. Avoid individual autos who refuse meter."
        ),
        ShareStand(
            id = "ss_04",
            station = "Dadar Station (West)",
            destination = "Prabhadevi / Siddhivinayak Temple",
            vehicleType = "Kaali-Peeli Taxi",
            farePerSeat = 25,
            standLocation = "Near Senapati Bapat Marg corner",
            avgWaitMins = 5,
            tips = "Runs continuously throughout the day. Fixed ₹25 per passenger."
        ),
        ShareStand(
            id = "ss_05",
            station = "Ghatkopar Station (West)",
            destination = "R-City Mall / LBS Marg",
            vehicleType = "Auto",
            farePerSeat = 20,
            standLocation = "Exit 2 under Metro Escalator",
            avgWaitMins = 3,
            tips = "Direct connectivity to LBS Marg commercial complexes."
        ),
        ShareStand(
            id = "ss_06",
            station = "Malad Station (West)",
            destination = "Mindspace IT Park / Link Road",
            vehicleType = "Auto",
            farePerSeat = 25,
            standLocation = "Station road auto stand near Subway",
            avgWaitMins = 3,
            tips = "Ideal for BPO/Tech workers heading to Mindspace buildings."
        )
    )

    fun getAllTrainSchedules(): List<TrainSchedule> = trainSchedules

    fun getTrainsByLine(line: TrainLine): List<TrainSchedule> =
        trainSchedules.filter { it.line == line }

    fun getAllBusRoutes(): List<BusRoute> = busRoutes

    fun getAllShareStands(): List<ShareStand> = shareStands

    fun getPopularStations(): List<String> = listOf(
        "Churchgate", "CSMT", "Dadar", "Bandra", "Andheri",
        "Ghatkopar", "Kurla", "BKC", "Powai", "Thane",
        "Borivali", "Vashi", "Lower Parel", "Malad"
    )

    fun planRoutes(origin: String, destination: String): List<RoutePlan> {
        val o = origin.trim().lowercase()
        val d = destination.trim().lowercase()

        // Provide smart route plans based on typical Mumbai transit dynamics
        val routes = mutableListOf<RoutePlan>()

        // 1. Local Train + Share Auto Route (Fastest / Local Favorite)
        routes.add(
            RoutePlan(
                id = "plan_fastest",
                title = "Local Train (Fast) + Station Auto",
                tag = "Fastest",
                totalTimeMins = 38,
                totalFare = 40,
                steps = listOf(
                    TransitStep(
                        mode = TransitType.LOCAL_TRAIN,
                        title = "Take Fast Local Train",
                        instruction = "Board Fast train from $origin towards Dadar/Kurla transfer point.",
                        durationMins = 24,
                        fareApprox = 10,
                        lineOrRoute = "WR / CR Fast Local",
                        lineHex = 0xFFE11D48
                    ),
                    TransitStep(
                        mode = TransitType.AUTO_TAXI,
                        title = "Station Share Auto",
                        instruction = "Board official share-auto stand towards $destination. Fixed rate per seat.",
                        durationMins = 12,
                        fareApprox = 30,
                        lineOrRoute = "Share Auto Stand",
                        lineHex = 0xFFD97706
                    )
                ),
                crowdWarning = "High crowd between 8:30-10:30 AM. Board from rear coaches for easier exit.",
                mapsQueryOrigin = origin,
                mapsQueryDestination = destination
            )
        )

        // 2. Metro / AC Local Hybrid (Most Comfortable)
        routes.add(
            RoutePlan(
                id = "plan_comfortable",
                title = "AC Local / Metro Line 3 Transit",
                tag = "AC & Comfortable",
                totalTimeMins = 44,
                totalFare = 85,
                steps = listOf(
                    TransitStep(
                        mode = TransitType.LOCAL_TRAIN,
                        title = "AC Local Train / Metro 3",
                        instruction = "Board air-conditioned local or Aqua Metro line. Cool, secure, and relaxed.",
                        durationMins = 28,
                        fareApprox = 65,
                        lineOrRoute = "AC Suburban / Metro 3",
                        lineHex = 0xFF0284C7
                    ),
                    TransitStep(
                        mode = TransitType.BEST_BUS,
                        title = "Connecting AC Electric Bus",
                        instruction = "Walk 2 mins to bus stop; board AC Feeder Bus to $destination.",
                        durationMins = 14,
                        fareApprox = 20,
                        lineOrRoute = "BEST AC Feeder",
                        lineHex = 0xFFDC2626
                    )
                ),
                crowdWarning = "Low to moderate crowd. Guaranteed seats on off-peak hours.",
                mapsQueryOrigin = origin,
                mapsQueryDestination = destination
            )
        )

        // 3. Ultra-Budget (Regular 2nd Class Train + BEST Ordinary)
        routes.add(
            RoutePlan(
                id = "plan_cheapest",
                title = "Super-Saver (Local Train II Class + Walk)",
                tag = "Cheapest",
                totalTimeMins = 49,
                totalFare = 15,
                steps = listOf(
                    TransitStep(
                        mode = TransitType.LOCAL_TRAIN,
                        title = "2nd Class Local Train",
                        instruction = "Single ticket ₹5 to ₹10 via UTS app. Follow Slow/Fast train indicator board.",
                        durationMins = 32,
                        fareApprox = 5,
                        lineOrRoute = "Mumbai Suburban Railway",
                        lineHex = 0xFF16A34A
                    ),
                    TransitStep(
                        mode = TransitType.BEST_BUS,
                        title = "Ordinary BEST Bus / Walk",
                        instruction = "Take ordinary red bus (₹6) or 10 min brisk walk to $destination.",
                        durationMins = 15,
                        fareApprox = 10,
                        lineOrRoute = "BEST Bus",
                        lineHex = 0xFF16A34A
                    )
                ),
                crowdWarning = "Budget route. Stand clear of doors while train is entering platform.",
                mapsQueryOrigin = origin,
                mapsQueryDestination = destination
            )
        )

        return routes
    }
}
