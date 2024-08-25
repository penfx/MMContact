package com.mm.contact.di


import com.mm.contact.domain.repository.ContactsRepository
import com.mm.contact.domain.usecase.ContactUseCase
import com.mm.contact.domain.usecase.ContactUseCaseImp
import com.mm.contact.helpers.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Provides
    fun provideContactUseCase(contactsRepository: ContactsRepository):ContactUseCase = ContactUseCaseImp(contactsRepository)

}