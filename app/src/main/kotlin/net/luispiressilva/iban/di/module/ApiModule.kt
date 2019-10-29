package net.luispiressilva.iban.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import net.luispiressilva.iban.BuildConfig
import net.luispiressilva.iban.app.API_TIMEOUT
import net.luispiressilva.iban.network.ApiProvider
import net.luispiressilva.iban.network.EndpointCollection
import net.luispiressilva.iban.network.adapter.CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {



    @Singleton
    @Provides
    fun providesCallAdapterFactory() = CallAdapterFactory()


    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(httpLoggingInterceptor)

        return client.build()
    }


    @Singleton
    @Provides
    fun providesHttpInterceptor(
//        accountUtils: AccountUtils
    ) = Interceptor {
        var requestBuilder = it.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        requestBuilder.header("Accept", "application/json")
//        if (accountUtils.isLogged()) {
//            requestBuilder.header("Authorization", "Bearer ${accountUtils.getToken()}")
//        }
        return@Interceptor it.proceed(requestBuilder.build())
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
                .build()

    }

    @Provides
    @Singleton
    fun provideRetrofit(callAdapterFactory: CallAdapterFactory, moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(callAdapterFactory)
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(okHttpClient)
                .build()
    }


    @Provides
    @Singleton
    fun provideEndpointCollection(retrofit: Retrofit): EndpointCollection = retrofit.create(EndpointCollection::class.java)

    @Provides
    fun providesApiProvider(service: EndpointCollection) = ApiProvider(service)


}