package com.mm.contact.domain.repository

import com.mm.contact.data.entities.Result
import com.mm.contact.domain.model.Contact

interface ContactsRepository {
    suspend fun getAllContact(
        page: Int, pageSize: Int, searchQuery: String? = null
    ): Result<List<Contact>>
}