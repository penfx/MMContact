package com.mm.contact.di

import com.mm.contact.data.local.MMDatabase
import com.mm.contact.data.repository.ContactsRepositoryImp
import com.mm.contact.domain.repository.ContactsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(contactDb: MMDatabase,): ContactsRepository = ContactsRepositoryImp(contactDb)
}