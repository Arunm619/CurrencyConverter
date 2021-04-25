package io.arunbuilds.currencyconverter.data

import io.arunbuilds.currencyconverter.data.models.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("/latest?access_key={API_KEY}")
    suspend fun getRates(
        @Query("base") base: String,
        @Path("API_KEY") key: String
    ): Response<Currency>
}