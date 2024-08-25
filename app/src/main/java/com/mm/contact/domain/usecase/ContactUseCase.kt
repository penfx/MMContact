package com.mm.contact.domain.usecase

import com.mm.contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactUseCase {
    suspend fun getAllContact(): Flow<List<Contact>>
    suspend fun getContacts(page: Int): Flow<List<Contact>>
    fun searchContacts(query: String?): Flow<List<Contact>>
    suspend fun createContact(contact: Contact)
}