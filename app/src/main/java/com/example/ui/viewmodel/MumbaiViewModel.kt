package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SavedCommuteEntity
import com.example.data.local.SavedStayEntity
import com.example.data.model.*
import com.example.data.repository.AccommodationRepository
import com.example.data.repository.GuideRepository
import com.example.data.repository.LocalStayRepository
import com.example.data.repository.MumbaiTransportRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class AppNavTab(val title: String) {
    HOME("Home"),
    TRANSIT("Live Transit"),
    STAYS("Find Stay"),
    ROUTES("Route Planner"),
    COST_MATRIX("Cost Compare"),
    GUIDE("Survival Guide"),
    SAVED("Saved")
}

data class TransitUiState(
    val selectedTab: TransitType = TransitType.LOCAL_TRAIN,
    val selectedTrainLine: TrainLine = TrainLine.WESTERN,
    val searchQuery: String = "",
    val trains: List<TrainSchedule> = emptyList(),
    val buses: List<BusRoute> = emptyList(),
    val shareStands: List<ShareStand> = emptyList(),
    val isACFilterOnly: Boolean = false,
    val isFastOnly: Boolean = false
)

data class StayUiState(
    val searchQuery: String = "",
    val selectedArea: String = "All Areas",
    val selectedType: StayType = StayType.ALL,
    val selectedGender: GenderSuitability = GenderSuitability.ANY,
    val maxBudget: Int = 30000,
    val maxDistanceKm: Double = 5.0,
    val accommodations: List<Accommodation> = emptyList(),
    val selectedStay: Accommodation? = null
)

data class RoutePlannerUiState(
    val origin: String = "Andheri",
    val destination: String = "BKC",
    val plannedRoutes: List<RoutePlan> = emptyList(),
    val isCalculating: Boolean = false
)

class MumbaiViewModel(application: Application) : AndroidViewModel(application) {

    private val transportRepo = MumbaiTransportRepository()
    private val stayRepo = AccommodationRepository()
    private val guideRepo = GuideRepository()
    private val localRepo = LocalStayRepository(AppDatabase.getDatabase(application).stayDao())

    // Active screen navigation
    private val _currentNavTab = MutableStateFlow(AppNavTab.HOME)
    val currentNavTab: StateFlow<AppNavTab> = _currentNavTab.asStateFlow()

    // Transit state
    private val _transitState = MutableStateFlow(
        TransitUiState(
            trains = transportRepo.getTrainsByLine(TrainLine.WESTERN),
            buses = transportRepo.getAllBusRoutes(),
            shareStands = transportRepo.getAllShareStands()
        )
    )
    val transitState: StateFlow<TransitUiState> = _transitState.asStateFlow()

    // Accommodation state
    private val _stayState = MutableStateFlow(
        StayUiState(accommodations = stayRepo.getAllAccommodations())
    )
    val stayState: StateFlow<StayUiState> = _stayState.asStateFlow()

    // Route Planner state
    private val _routeState = MutableStateFlow(
        RoutePlannerUiState(
            plannedRoutes = transportRepo.planRoutes("Andheri", "BKC")
        )
    )
    val routeState: StateFlow<RoutePlannerUiState> = _routeState.asStateFlow()

    // Suburb Cost Matrix state
    val suburbCostIndices: List<SuburbCostIndex> = stayRepo.getSuburbCostIndices()
    val emergencyContacts = guideRepo.emergencyContacts
    val survivalTips = guideRepo.survivalTips
    val fareRules = guideRepo.fareRules
    val popularStations = transportRepo.getPopularStations()
    val availableAreas = stayRepo.getAllAreas()

    // Saved database entities
    val savedStays: StateFlow<List<SavedStayEntity>> = localRepo.savedStays
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedCommutes: StateFlow<List<SavedCommuteEntity>> = localRepo.savedCommutes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectNavTab(tab: AppNavTab) {
        _currentNavTab.value = tab
    }

    // Transit actions
    fun setTransitTab(type: TransitType) {
        _transitState.update { it.copy(selectedTab = type) }
        applyTransitFilter()
    }

    fun setTrainLine(line: TrainLine) {
        _transitState.update { it.copy(selectedTrainLine = line) }
        applyTransitFilter()
    }

    fun setTransitSearch(query: String) {
        _transitState.update { it.copy(searchQuery = query) }
        applyTransitFilter()
    }

    fun toggleACFilter() {
        _transitState.update { it.copy(isACFilterOnly = !it.isACFilterOnly) }
        applyTransitFilter()
    }

    fun toggleFastFilter() {
        _transitState.update { it.copy(isFastOnly = !it.isFastOnly) }
        applyTransitFilter()
    }

    private fun applyTransitFilter() {
        val current = _transitState.value
        val allTrains = transportRepo.getTrainsByLine(current.selectedTrainLine)
        val filteredTrains = allTrains.filter { train ->
            val matchQuery = current.searchQuery.isBlank() ||
                    train.trainNumber.contains(current.searchQuery, ignoreCase = true) ||
                    train.destination.contains(current.searchQuery, ignoreCase = true) ||
                    train.origin.contains(current.searchQuery, ignoreCase = true) ||
                    train.stopsSummary.contains(current.searchQuery, ignoreCase = true)
            val matchAC = !current.isACFilterOnly || train.isAC
            val matchFast = !current.isFastOnly || train.isFast
            matchQuery && matchAC && matchFast
        }

        val allBuses = transportRepo.getAllBusRoutes()
        val filteredBuses = allBuses.filter { bus ->
            current.searchQuery.isBlank() ||
                    bus.routeNumber.contains(current.searchQuery, ignoreCase = true) ||
                    bus.origin.contains(current.searchQuery, ignoreCase = true) ||
                    bus.destination.contains(current.searchQuery, ignoreCase = true) ||
                    bus.via.contains(current.searchQuery, ignoreCase = true)
        }

        val allStands = transportRepo.getAllShareStands()
        val filteredStands = allStands.filter { stand ->
            current.searchQuery.isBlank() ||
                    stand.station.contains(current.searchQuery, ignoreCase = true) ||
                    stand.destination.contains(current.searchQuery, ignoreCase = true)
        }

        _transitState.update {
            it.copy(
                trains = filteredTrains,
                buses = filteredBuses,
                shareStands = filteredStands
            )
        }
    }

    // Accommodation actions
    fun setStaySearch(query: String) {
        _stayState.update { it.copy(searchQuery = query) }
        applyStayFilter()
    }

    fun setStayArea(area: String) {
        _stayState.update { it.copy(selectedArea = area) }
        applyStayFilter()
    }

    fun setStayType(type: StayType) {
        _stayState.update { it.copy(selectedType = type) }
        applyStayFilter()
    }

    fun setStayGender(gender: GenderSuitability) {
        _stayState.update { it.copy(selectedGender = gender) }
        applyStayFilter()
    }

    fun setMaxBudget(budget: Int) {
        _stayState.update { it.copy(maxBudget = budget) }
        applyStayFilter()
    }

    fun setSelectedStay(stay: Accommodation?) {
        _stayState.update { it.copy(selectedStay = stay) }
    }

    private fun applyStayFilter() {
        val current = _stayState.value
        val filtered = stayRepo.filterAccommodations(
            maxRent = current.maxBudget,
            area = current.selectedArea,
            stayType = current.selectedType,
            gender = current.selectedGender,
            maxStationDistanceKm = current.maxDistanceKm
        ).filter { stay ->
            current.searchQuery.isBlank() ||
                    stay.title.contains(current.searchQuery, ignoreCase = true) ||
                    stay.area.contains(current.searchQuery, ignoreCase = true) ||
                    stay.address.contains(current.searchQuery, ignoreCase = true) ||
                    stay.nearestStation.contains(current.searchQuery, ignoreCase = true)
        }
        _stayState.update { it.copy(accommodations = filtered) }
    }

    // Route Planner actions
    fun updateOrigin(origin: String) {
        _routeState.update { it.copy(origin = origin) }
    }

    fun updateDestination(destination: String) {
        _routeState.update { it.copy(destination = destination) }
    }

    fun swapRouteEndpoints() {
        _routeState.update {
            val oldOrigin = it.origin
            val oldDest = it.destination
            it.copy(origin = oldDest, destination = oldOrigin)
        }
        calculateRoutes()
    }

    fun calculateRoutes() {
        val current = _routeState.value
        val plans = transportRepo.planRoutes(current.origin, current.destination)
        _routeState.update { it.copy(plannedRoutes = plans) }
    }

    // Local DB Saved Stays
    fun toggleSaveStay(stay: Accommodation, note: String = "") {
        viewModelScope.launch {
            val existing = savedStays.value.find { it.id == stay.id }
            if (existing != null) {
                localRepo.removeStay(stay.id)
            } else {
                localRepo.saveStay(
                    SavedStayEntity(
                        id = stay.id,
                        title = stay.title,
                        area = stay.area,
                        monthlyRent = stay.monthlyRent,
                        stayType = stay.stayType.label,
                        nearestStation = stay.nearestStation,
                        contactPhone = stay.contactPhone,
                        userNote = note
                    )
                )
            }
        }
    }

    fun removeSavedStay(id: String) {
        viewModelScope.launch {
            localRepo.removeStay(id)
        }
    }

    fun updateStayNote(id: String, note: String) {
        viewModelScope.launch {
            localRepo.updateNote(id, note)
        }
    }

    fun saveCommuteRoute(source: String, destination: String, mode: String, mins: Int, fare: Int, label: String) {
        viewModelScope.launch {
            localRepo.saveCommute(
                SavedCommuteEntity(
                    source = source,
                    destination = destination,
                    preferredMode = mode,
                    estTimeMins = mins,
                    estFare = fare,
                    label = label
                )
            )
        }
    }

    fun deleteCommute(id: Int) {
        viewModelScope.launch {
            localRepo.deleteCommute(id)
        }
    }
}
