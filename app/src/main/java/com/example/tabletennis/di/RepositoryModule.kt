package com.example.tabletennis.di

import com.example.tabletennis.data.repository.DatabaseReposImpl
import com.example.tabletennis.data.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesDatabaseRepository(databaseReposImpl: DatabaseReposImpl): DatabaseRepository =
        databaseReposImpl
}