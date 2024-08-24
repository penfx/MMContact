package com.mm.contact.data.repository

import com.mm.contact.data.Result
import com.mm.contact.data.local.MMDatabase
import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.data.toModel
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import com.mm.contact.helpers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsRepositoryImp(
    private val contactDb: MMDatabase,

    ) : ContactsRepository {
    @Inject
    @IoDispatcher
    lateinit var coroutineDispatcher: CoroutineDispatcher

    override suspend fun getAllContact(
        page: Int,
        pageSize: Int,
        searchQuery: String?
    ): Flow<List<Contact>> = contactDb.getContactDao()
        .getContacts()
        .map { it.map { c -> c.toModel() } }

    override suspend fun insertContact(contact: ContactEntity) {
        return contactDb.getContactDao().insertContact(contact)
    }

}