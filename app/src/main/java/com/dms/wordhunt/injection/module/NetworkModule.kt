package com.dms.wordhunt.injection.module

import android.content.Context
import com.arenberg.eye.data.encryptor.EncryptedStorageFabric
import com.arkub.unified.api.apicoordinator.EncryptedStorage
import com.dms.wordhunt.classes.SERVER_URL
import com.dms.wordhunt.classes.ServerResponse
import com.dms.wordhunt.internet.api.Api
import com.dms.wordhunt.internet.api.AuthInterceptor
import com.dms.wordhunt.internet.api.RetrofitErrorInterceptorConstructor
import com.dms.wordhunt.internet.api.RxErrorHandlingCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Module which provides all required dependencies about network
 */
@Module
class NetworkModule {
    /**
     * Provides the API implementation.
     * @param retrofit the Retrofit object used to create the API instance
     * @return the API implementation.
     */
    @Provides
    @Reusable
    internal fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    internal fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()


    }

    /**
     * Provides the HTTP client.
     * @return the HTTP client
     */
    @Provides
    @Reusable
    internal fun provideHttpClient(authInterceptor: AuthInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor, retrofitErrorInterceptor: RetrofitErrorInterceptorConstructor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(retrofitErrorInterceptor)

        httpClientBuilder.hostnameVerifier(object : HostnameVerifier
        {
            override fun verify(hostname: String, session: SSLSession): Boolean
            {
                return true
            }
        })

        httpClientBuilder.addInterceptor(httpLoggingInterceptor)

        return httpClientBuilder.build()
    }

    /**
     * Provides the AuthInterceptor object.
     * @return the AuthInterceptor object
     */
    @Provides
    @Reusable
    internal fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    /**
     * Provides the HttpLoggingInterceptor object.
     * @return the HttpLoggingInterceptor object
     */
    @Provides
    @Reusable
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * Provides the RetrofitErrorInterceptor object.
     * @return the RetrofitErrorInterceptor object
     */
    @Provides
    @Reusable
    internal fun provideRetrofitErrorInterceptor(): RetrofitErrorInterceptorConstructor {
        return RetrofitErrorInterceptorConstructor()
    }


    /**
     * Provides the EncryptedStorage object.
     * @return the EncryptedStorage object
     */
    @Provides
    @Singleton
    internal fun provideEncryptedStorage(context: Context): EncryptedStorage {
        return EncryptedStorageFabric.getEncryptedStorage(context)
    }

    /**
     * Provides the Retrofit Converter factory object.
     * @return the Retrofit Converter factory object
     */
    @Provides
    @Reusable
    internal fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides the Retrofit Call Adapter factory object.
     * @return the Retrofit Call Adapter factory object
     */
    @Provides
    @Reusable
    internal fun provideCallAdapterFactory(): CallAdapter.Factory {
        return RxErrorHandlingCallAdapterFactory.create()
    }

}