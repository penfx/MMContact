package com.mm.contact.domain.repository

import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getAllContacts():Flow<List<Contact>>
    suspend fun getContacts(
        page: Int
    ): Flow<List<Contact>>

    suspend fun insertContact(toEntity: ContactEntity)
}