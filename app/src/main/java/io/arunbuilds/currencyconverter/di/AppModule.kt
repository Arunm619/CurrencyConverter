package io.arunbuilds.currencyconverter.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.arunbuilds.currencyconverter.R
import io.arunbuilds.currencyconverter.data.CurrencyAPI
import io.arunbuilds.currencyconverter.main.DefaultMainRepository
import io.arunbuilds.currencyconverter.main.MainRepository
import io.arunbuilds.currencyconverter.util.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL: String = "https://api.ratesapi.io/"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyAPI(okHttpClient: OkHttpClient): CurrencyAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(CurrencyAPI::class.java)


    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor().apply{
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) =  OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()


    @Singleton
    @Provides
    fun provideMainRepository(
        api: CurrencyAPI,
        apiKey: String
    ): MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun providesAPIKeyString(
        @ApplicationContext app: Context
    ) = app.getString(R.string.api_key)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }
}