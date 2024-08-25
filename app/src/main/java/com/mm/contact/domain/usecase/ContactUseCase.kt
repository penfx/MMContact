package com.mm.contact.domain.usecase

import com.mm.contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactUseCase {
    suspend fun getAllContact(pageSize: Int, page: Int, searchQuery: String?): Flow<List<Contact>>
    fun getAllContact(): Flow<List<Contact>>
    suspend fun createContact(contact: Contact)
}