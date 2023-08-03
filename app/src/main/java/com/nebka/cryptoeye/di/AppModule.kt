package com.nebka.cryptoeye.di

import android.content.Context
import androidx.room.Room
import com.nebka.cryptoeye.BuildConfig
import com.nebka.cryptoeye.data.db.CryptoEyeDatabase
import com.nebka.cryptoeye.data.remote.api.TagApi
import com.nebka.cryptoeye.util.Constants.AUTHENTICATION_TOKEN
import com.nebka.cryptoeye.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.HEADERS)
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideRequestInterceptor() = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", AUTHENTICATION_TOKEN)
            .build()
        chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        requestInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor).apply {
            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)
        }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        synchronized(CryptoEyeDatabase::class) {
            Room.databaseBuilder(
                context,
                CryptoEyeDatabase::class.java,
                "CryptoEyeDatabase"
            ).build()
        }

    @Singleton
    @Provides
    fun provideTagApi(retrofit: Retrofit): TagApi = retrofit.create(TagApi::class.java)

    @Singleton
    @Provides
    fun provideTagDao(cryptoEyeDatabase: CryptoEyeDatabase) = cryptoEyeDatabase.tagDao()
}