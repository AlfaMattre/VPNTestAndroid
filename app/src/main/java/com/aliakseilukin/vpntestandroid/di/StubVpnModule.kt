package com.aliakseilukin.vpntestandroid.di

import com.aliakseilukin.vpntestandroid.data.data_source.remote.StubVpnDataSource
import com.aliakseilukin.vpntestandroid.data.data_source.remote.StubVpnDataSourceImpl
import com.aliakseilukin.vpntestandroid.data.repository.StubVpnRepositoryImpl
import com.aliakseilukin.vpntestandroid.domain.repository.StubVpnRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VpnModule {

    @Binds
    @Singleton
    abstract fun bindVpnDataSource(
        impl: StubVpnDataSourceImpl
    ): StubVpnDataSource

    @Binds
    @Singleton
    abstract fun bindVpnRepository(
        impl: StubVpnRepositoryImpl
    ): StubVpnRepository
}