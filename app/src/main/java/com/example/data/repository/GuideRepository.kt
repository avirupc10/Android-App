package com.example.data.repository

import com.example.data.model.EmergencyContact
import com.example.data.model.FareRuleInfo
import com.example.data.model.MumbaiSurvivalTip

class GuideRepository {

    val emergencyContacts = listOf(
        EmergencyContact(
            title = "Railway Police (GRP)",
            number = "1512",
            description = "Toll-free 24/7 emergency response for Mumbai local trains & station premises",
            category = "Railway"
        ),
        EmergencyContact(
            title = "Railway Protection Force (RPF)",
            number = "139",
            description = "Integrated Indian Railways helpline for passenger security & onboard theft/harassment",
            category = "Railway"
        ),
        EmergencyContact(
            title = "Women Helpline Mumbai",
            number = "103",
            description = "Dedicated Mumbai Police women safety cell, quick squad dispatch",
            category = "Women Safety"
        ),
        EmergencyContact(
            title = "Mumbai Police Emergency",
            number = "112",
            description = "Centralized quick police and disaster response control room",
            category = "Police"
        ),
        EmergencyContact(
            title = "Medical Emergency Ambulance",
            number = "108",
            description = "Free emergency ambulance service across Mumbai Metropolitan Region",
            category = "Medical"
        ),
        EmergencyContact(
            title = "BEST Bus Helpline",
            number = "1800227550",
            description = "Toll-free route inquiries, lost property, and digital pass assistance",
            category = "City Helpline"
        )
    )

    val survivalTips = listOf(
        MumbaiSurvivalTip(
            id = "tip_01",
            title = "The Golden Rule of Mumbai Local: Boarding Etiquette",
            category = "Local Train Rules",
            summary = "Always ask 'Utarna hai kya?' (Are you getting down?) before blocking the door.",
            fullDetails = "In a packed Mumbai local train, passengers queue up inside the coach 2 stations before their stop. If you are standing near the door and not getting down, people behind you will assume you are exiting. Always move aside or step out briefly onto the platform to let passengers alight, then re-enter immediately.",
            iconName = "train"
        ),
        MumbaiSurvivalTip(
            id = "tip_02",
            title = "Fast vs Slow Trains Demystified",
            category = "Local Train Rules",
            summary = "Fast trains skip smaller stations. Look for 'F' vs 'S' indicator on station boards.",
            fullDetails = "Fast locals on Western line halt only at major hubs (e.g. Churchgate, Mumbai Central, Dadar, Bandra, Andheri, Borivali). If your stop is Khar, Santacruz, or Matunga, you MUST take a Slow ('S') train. On Central line, Fast trains skip between Byculla, Dadar, Kurla, Ghatkopar, and Thane.",
            iconName = "speed"
        ),
        MumbaiSurvivalTip(
            id = "tip_03",
            title = "UTS Mobile App & Cashless Tickets",
            category = "Ticket Booking",
            summary = "Never stand in long ticket counter queues! Use UTS on Mobile or ATVM QR codes.",
            fullDetails = "Download the official 'UTS on mobile' app (CR & WR). You can buy paperless tickets, season passes, and platform tickets within 2 km of the station (outside station tracks). You can also tap and pay using UPI at automatic ATVM ticket vending machines at all stations.",
            iconName = "confirmation_number"
        ),
        MumbaiSurvivalTip(
            id = "tip_04",
            title = "Auto Rickshaw Fare Rules (Suburbs)",
            category = "Buses & Autos",
            summary = "Autos run strictly by meter in Mumbai suburbs (Bandra/Kurla northward). Minimum fare is ₹23.",
            fullDetails = "Autos are NOT allowed in South Mumbai (south of Bandra/Sion). In suburbs, drivers cannot refuse by law if you hail them at an official stand. Flag-down minimum fare is ₹23 for first 1.5 km, then approx ₹15.33 per km. Midnight tariff (12 AM - 5 AM) has an official 25% surcharge.",
            iconName = "local_taxi"
        ),
        MumbaiSurvivalTip(
            id = "tip_05",
            title = "BEST Bus Chalo App & Super-Saver Passes",
            category = "Buses & Autos",
            summary = "Single bus ride is only ₹6. Daily unlimited bus pass costs just ₹50!",
            fullDetails = "BEST bus fares are among the lowest in the world. Ordinary buses start at ₹6, while AC Electric buses start at ₹6 for 5 km! Use the 'Chalo App' for live bus tracking, digital ticketing, and unlimited daily/monthly passes.",
            iconName = "directions_bus"
        ),
        MumbaiSurvivalTip(
            id = "tip_06",
            title = "Finding Flatmates & Avoiding High Brokerage",
            category = "Renting Hacks",
            summary = "Typical brokerage in Mumbai is 1 month rent. Use verified owner listings & flatmate groups.",
            fullDetails = "In Mumbai, standard security deposit is 2 to 3 months for bachelor PGs and 5 to 10 months for family apartments in South Mumbai. Always ask if electricity and maintenance are included in PG rent. Check water supply timings before finalizing an apartment!",
            iconName = "home"
        )
    )

    val fareRules = listOf(
        FareRuleInfo(
            mode = "Auto Rickshaw (Suburbs)",
            baseFare = "₹23.00",
            baseDistance = "First 1.5 km",
            perKmRate = "₹15.33 / km",
            nightSurcharge = "25% extra (12:00 AM - 05:00 AM)",
            importantNotice = "Strictly by digital electronic meter. Not permitted south of Mahim/Sion."
        ),
        FareRuleInfo(
            mode = "Kaali-Peeli Taxi (Entire Mumbai)",
            baseFare = "₹28.00",
            baseDistance = "First 1.5 km",
            perKmRate = "₹18.66 / km",
            nightSurcharge = "25% extra (12:00 AM - 05:00 AM)",
            importantNotice = "Meter reading is mandatory. Share taxi stands have fixed regulated rates per passenger."
        ),
        FareRuleInfo(
            mode = "BEST Ordinary Bus",
            baseFare = "₹6.00",
            baseDistance = "Up to 5 km",
            perKmRate = "₹10 (10 km), ₹15 (15 km)",
            nightSurcharge = "None",
            importantNotice = "Daily pass ₹50 allows unlimited travel across all non-AC routes."
        ),
        FareRuleInfo(
            mode = "BEST AC Bus",
            baseFare = "₹6.00",
            baseDistance = "Up to 5 km",
            perKmRate = "₹13 (10 km), ₹19 (15 km)",
            nightSurcharge = "None",
            importantNotice = "Daily AC pass ₹60 gives unlimited access to both AC and non-AC buses."
        )
    )
}
