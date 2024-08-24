package com.mm.contact.domain.usecase


import com.mm.contact.data.entities.Result
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository

class ContactUseCaseImp(private val contactsRepository: ContactsRepository) : ContactUseCase {
    override suspend fun getAllContact(
        pageSize: Int,
        page: Int,
        searchQuery: String?
    ): Result<List<Contact>> {
        return contactsRepository.getAllContact(
            page = page,
            pageSize = pageSize,
            searchQuery = searchQuery
        )
    }
}