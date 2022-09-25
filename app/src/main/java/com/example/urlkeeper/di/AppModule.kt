package com.example.urlkeeper.di

import com.example.urlkeeper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProvideModule {

    @Singleton
    @Provides
    fun provideOgpUseCase(htmlParseRepository: HTMLParseRepository): OgpParseUseCase {
        return OgpParseUseCase(htmlParseRepository = htmlParseRepository)
    }

    @Singleton
    @Provides
    fun provideRegisterUrlUseCase(urlRepository: UrlRepository): RegisterUrlUseCase {
        return RegisterUrlUseCase(urlRepository = urlRepository)
    }

    @Singleton
    @Provides
    fun provideUrlRepository(apollo: Apollo): UrlRepository {
        return UrlRepository(apolloClient = apollo.apolloClient)
    }
}