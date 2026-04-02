package com.aliakseilukin.vpntestandroid.di

import com.aliakseilukin.vpntestandroid.data.data_source.local.CountriesLocalDataSource
import com.aliakseilukin.vpntestandroid.data.data_source.local.CountriesLocalDataSourceImpl
import com.aliakseilukin.vpntestandroid.data.repository.NetworkRepositoryImpl
import com.aliakseilukin.vpntestandroid.data.data_source.remote.CountriesDataSource
import com.aliakseilukin.vpntestandroid.data.data_source.remote.CountriesDataSourceImpl
import com.aliakseilukin.vpntestandroid.domain.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindCountriesDataSource(
        impl: CountriesDataSourceImpl
    ): CountriesDataSource

    @Binds
    @Singleton
    abstract fun bindCountriesLocalDataSource(
        impl: CountriesLocalDataSourceImpl
    ): CountriesLocalDataSource


    @Binds
    @Singleton
    abstract fun bindNetworkRepository(
        impl: NetworkRepositoryImpl
    ): NetworkRepository
}