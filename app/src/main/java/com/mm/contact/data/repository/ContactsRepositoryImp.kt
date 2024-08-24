package com.mm.contact.data.repository

import com.mm.contact.data.entities.Result
import com.mm.contact.data.provider.ContactProvider
import com.mm.contact.data.toModel
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import com.mm.contact.helpers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ContactsRepositoryImp(
    private val contactProvider: ContactProvider,

) : ContactsRepository {
    @Inject
    @IoDispatcher
    lateinit var  coroutineDispatcher: CoroutineDispatcher

    override suspend fun getAllContact(
        page: Int,
        pageSize: Int,
        searchQuery: String?
    ): Result<List<Contact>> {

        with(coroutineDispatcher) {
            return when (val result = contactProvider.getContacts(page, pageSize, searchQuery)) {
                is Result.Success -> {
                    Result.Success(result.data.map { it.toModel() })
                }
                is Result.Error -> Result.Error(result.exception)
                Result.Loading -> Result.Loading
            }
        }
    }

}