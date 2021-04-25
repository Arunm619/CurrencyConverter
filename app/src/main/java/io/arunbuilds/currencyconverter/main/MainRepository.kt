package io.arunbuilds.currencyconverter.main

import io.arunbuilds.currencyconverter.data.models.CurrencyResponse
import io.arunbuilds.currencyconverter.util.Resource

interface MainRepository {
    suspend fun getRates(base: String): Resource<CurrencyResponse>
}