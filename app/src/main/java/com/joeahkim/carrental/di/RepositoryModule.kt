package com.joeahkim.carrental.di

import com.joeahkim.carrental.data.repository.BookingsRepository
import com.joeahkim.carrental.data.repository.BookingsRepositoryImpl
import com.joeahkim.carrental.data.repository.CarRepository
import com.joeahkim.carrental.data.repository.CarRepositoryImpl
import com.joeahkim.carrental.data.repository.HomeScreenRepository
import com.joeahkim.carrental.data.repository.HomeScreenRepositoryImpl
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

    @Provides
    @Singleton
    fun provideHomeScreenRepository(
        impl: HomeScreenRepositoryImpl
    ): HomeScreenRepository = impl

    @Provides
    @Singleton
    fun provideCarRepository(
        impl: CarRepositoryImpl
    ): CarRepository = impl
}