package com.mm.contact.di

import android.app.Application
import com.mm.contact.data.local.MMDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): MMDatabase {
        return MMDatabase.getDatabase(application)
    }
}