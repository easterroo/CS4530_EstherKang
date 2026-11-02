package com.example.assignment4

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FunFactRepo(
    private val dao: FunFactDao,
    private val client: HttpClient
) {
    fun getFunFacts(keyword: String): Flow<List<FunFact>> =
        if (keyword.isBlank()) dao.getAllFunFacts() else dao.search(keyword.trim())

    suspend fun fetchAndStoreFact(): Result<Unit> = try {
        val fact: FunFact = client.get("https://uselessfacts.jsph.pl/random.json") {
            parameter("language", "en")
        }.body()
        dao.insert(fact)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}