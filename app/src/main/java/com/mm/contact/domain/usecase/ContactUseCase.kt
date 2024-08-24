package com.mm.contact.domain.usecase

import com.mm.contact.data.entities.Result
import com.mm.contact.domain.model.Contact

interface ContactUseCase {
    suspend fun getAllContact(pageSize: Int, page: Int, searchQuery: String?): Result<List<Contact>>
}