package com.example.urlkeeper.di

import com.example.urlkeeper.HTMLParseRepository
import com.example.urlkeeper.OgpParseUseCase
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
}