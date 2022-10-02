package com.example.urlkeeper.di

import com.example.urlkeeper.Apollo
import com.example.urlkeeper.repository.HTMLParseRepository
import com.example.urlkeeper.repository.UrlRepository
import com.example.urlkeeper.usecase.OgpParseUseCase
import com.example.urlkeeper.usecase.RegisterUrlUseCase
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