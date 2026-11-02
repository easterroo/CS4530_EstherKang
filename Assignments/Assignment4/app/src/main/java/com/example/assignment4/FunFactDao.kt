package com.example.assignment4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {
    @Query("SELECT * FROM fun_facts ORDER BY id DESC")
    fun getAllFunFacts(): Flow<List<FunFact>>

    @Query("SELECT * FROM fun_facts WHERE text LIKE '%' || :keyword || '%' ORDER BY id DESC")
    fun search(keyword: String): Flow<List<FunFact>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fact: FunFact): Long
}