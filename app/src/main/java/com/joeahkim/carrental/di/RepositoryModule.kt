package com.joeahkim.carrental.di

import com.joeahkim.carrental.data.repository.BookingsRepository
import com.joeahkim.carrental.data.repository.BookingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import jakarta.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBookingsRepository(impl: BookingsRepositoryImpl): BookingsRepository = impl
}