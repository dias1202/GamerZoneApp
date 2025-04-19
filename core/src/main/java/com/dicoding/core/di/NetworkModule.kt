package com.dicoding.core.di

import android.util.Base64
import com.dicoding.core.BuildConfig
import com.dicoding.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import java.security.cert.X509Certificate

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    val hostname = "rawg.io"

    val certificatePinner = runBlocking {
        CertificatePinner.Builder().apply {
            getCertificatePins(hostname).forEach { pin ->
                add(hostname, pin)
            }
        }.build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    suspend fun getCertificatePins(hostname: String): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://$hostname")
                val connection = url.openConnection() as HttpsURLConnection
                connection.connect()

                val certs = connection.serverCertificates
                val hashList = mutableListOf<String>()

                for (cert in certs) {
                    if (cert is X509Certificate) {
                        val publicKey = cert.publicKey.encoded
                        val sha256 = MessageDigest.getInstance("SHA-256").digest(publicKey)
                        val pin = "sha256/${Base64.encodeToString(sha256, Base64.NO_WRAP)}"
                        hashList.add(pin)
                    }
                }
                hashList
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

}