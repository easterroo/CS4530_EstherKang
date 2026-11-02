package com.example.assignment4

import androidx.room.Room
import android.app.Application

class FunFactApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "funfacts.db"
        ).build()
    }

    val httpClient by lazy {APIClient.client}

    val repository: FunFactRepo by lazy {
        FunFactRepo(database.funFactDao(), httpClient)
    }
}