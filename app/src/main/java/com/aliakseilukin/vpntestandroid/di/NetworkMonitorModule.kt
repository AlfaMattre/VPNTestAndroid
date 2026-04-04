package com.aliakseilukin.vpntestandroid.di

import com.aliakseilukin.vpntestandroid.data.network_monitor.NetworkMonitorImpl
import com.aliakseilukin.vpntestandroid.domain.network_monitor.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkMonitorModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        impl: NetworkMonitorImpl
    ): NetworkMonitor
}