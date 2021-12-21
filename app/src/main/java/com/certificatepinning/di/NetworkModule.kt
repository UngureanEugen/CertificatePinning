package com.certificatepinning.di

import com.certificatepinning.BuildConfig
import com.certificatepinning.data.StoreService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.converter.htmlescape.HtmlEscapeStringConverter
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideTikXmlBuilder(): TikXml {
        return TikXml.Builder()
            .exceptionOnUnreadXml(false)
            .addTypeConverter(String::class.java, HtmlEscapeStringConverter())
            .build()
    }

    @Singleton
    @Provides
    fun provideCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add("rest.paysafecard.com", *BuildConfig.PINS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(tikXml: TikXml, certificatePinner: CertificatePinner): Retrofit.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .certificatePinner(certificatePinner)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(TikXmlConverterFactory.create(tikXml))
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        retrofit: Retrofit.Builder
    ): StoreService {
        return retrofit.build().create(StoreService::class.java)
    }
}
