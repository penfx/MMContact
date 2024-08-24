package com.mm.contact.di

import com.mm.contact.data.provider.ContactProvider
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
    fun provideRepository(contactProvider: ContactProvider): ContactsRepository = ContactsRepositoryImp(contactProvider)
}