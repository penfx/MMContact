package com.mm.contact.domain.repository

import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    suspend fun getAllContact(
        page: Int, pageSize: Int, searchQuery: String? = null
    ): Flow<List<Contact>>

    suspend fun insertContact(toEntity: ContactEntity)
}