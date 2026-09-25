package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_stays")
data class SavedStayEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val area: String,
    val monthlyRent: Int,
    val stayType: String,
    val nearestStation: String,
    val contactPhone: String,
    val userNote: String = "",
    val savedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_commutes")
data class SavedCommuteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val source: String,
    val destination: String,
    val preferredMode: String,
    val estTimeMins: Int,
    val estFare: Int,
    val label: String,
    val savedTimestamp: Long = System.currentTimeMillis()
)
