package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.GenderSuitability
import com.example.data.model.StayType
import com.example.data.model.TrainLine
import com.example.data.repository.AccommodationRepository
import com.example.data.repository.MumbaiTransportRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Aamchi Mumbai", appName)
    }

    @Test
    fun `verify transit repository returns western line schedules`() {
        val repo = MumbaiTransportRepository()
        val westernTrains = repo.getTrainsByLine(TrainLine.WESTERN)
        assertTrue(westernTrains.isNotEmpty())
        assertTrue(westernTrains.any { it.isFast })
    }

    @Test
    fun `verify route planning calculates options`() {
        val repo = MumbaiTransportRepository()
        val routes = repo.planRoutes("Andheri", "BKC")
        assertEquals(3, routes.size)
        assertTrue(routes.any { it.tag == "Fastest" })
        assertTrue(routes.any { it.tag == "Cheapest" })
    }

    @Test
    fun `verify accommodation filtering by budget and type`() {
        val repo = AccommodationRepository()
        val filtered = repo.filterAccommodations(
            maxRent = 12000,
            area = "All Areas",
            stayType = StayType.ALL,
            gender = GenderSuitability.ANY,
            maxStationDistanceKm = 5.0
        )
        assertTrue(filtered.isNotEmpty())
        assertTrue(filtered.all { it.monthlyRent <= 12000 })
    }
}
