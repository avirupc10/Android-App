package com.example.data.repository

import com.example.data.local.SavedCommuteEntity
import com.example.data.local.SavedStayEntity
import com.example.data.local.StayDao
import kotlinx.coroutines.flow.Flow

class LocalStayRepository(private val dao: StayDao) {

    val savedStays: Flow<List<SavedStayEntity>> = dao.getAllSavedStays()
    val savedCommutes: Flow<List<SavedCommuteEntity>> = dao.getAllSavedCommutes()

    fun isStaySaved(stayId: String): Flow<Boolean> = dao.isStaySaved(stayId)

    suspend fun saveStay(stay: SavedStayEntity) {
        dao.saveStay(stay)
    }

    suspend fun removeStay(stayId: String) {
        dao.removeSavedStay(stayId)
    }

    suspend fun updateNote(stayId: String, note: String) {
        dao.updateStayNote(stayId, note)
    }

    suspend fun saveCommute(commute: SavedCommuteEntity) {
        dao.saveCommute(commute)
    }

    suspend fun deleteCommute(id: Int) {
        dao.deleteCommute(id)
    }
}
