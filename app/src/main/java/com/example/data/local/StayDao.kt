package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StayDao {

    // Saved Stays
    @Query("SELECT * FROM saved_stays ORDER BY savedTimestamp DESC")
    fun getAllSavedStays(): Flow<List<SavedStayEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_stays WHERE id = :id)")
    fun isStaySaved(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStay(stay: SavedStayEntity)

    @Query("DELETE FROM saved_stays WHERE id = :id")
    suspend fun removeSavedStay(id: String)

    @Query("UPDATE saved_stays SET userNote = :note WHERE id = :id")
    suspend fun updateStayNote(id: String, note: String)

    // Saved Commutes
    @Query("SELECT * FROM saved_commutes ORDER BY savedTimestamp DESC")
    fun getAllSavedCommutes(): Flow<List<SavedCommuteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCommute(commute: SavedCommuteEntity)

    @Query("DELETE FROM saved_commutes WHERE id = :id")
    suspend fun deleteCommute(id: Int)
}
