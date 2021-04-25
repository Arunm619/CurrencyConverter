package io.arunbuilds.currencyconverter.main

import io.arunbuilds.currencyconverter.data.CurrencyAPI
import io.arunbuilds.currencyconverter.data.models.CurrencyResponse
import io.arunbuilds.currencyconverter.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: CurrencyAPI
) : MainRepository {
    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null)
                Resource.Success(result)
            else
                Resource.Error(response.message() ?: "An error occurred in getRates")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred in getRates")
        }
    }
}