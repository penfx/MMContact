package com.mm.contact.domain.usecase


import com.mm.contact.data.toEntity
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow

class ContactUseCaseImp(private val contactsRepository: ContactsRepository) : ContactUseCase {
    override suspend fun getAllContact(
        pageSize: Int,
        page: Int,
        searchQuery: String?
    ): Flow<List<Contact>> {
        return contactsRepository.getAllContact(
            page = page,
            pageSize = pageSize,
            searchQuery = searchQuery
        )
    }

    override suspend fun createContact(contact: Contact) {
        return contactsRepository.insertContact(contact.toEntity())
    }
}