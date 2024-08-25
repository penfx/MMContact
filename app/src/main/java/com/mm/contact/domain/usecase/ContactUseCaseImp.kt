package com.mm.contact.domain.usecase


import com.mm.contact.data.toEntity
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.repository.ContactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import me.xdrop.fuzzywuzzy.FuzzySearch

class ContactUseCaseImp(private val contactsRepository: ContactsRepository) : ContactUseCase {
    private var contacts: ArrayList<Contact> = ArrayList<Contact>()

    override suspend fun getAllContact(
        pageSize: Int,
        page: Int,
        searchQuery: String?
    ): Flow<List<Contact>> {

        if (searchQuery.isNullOrEmpty()) {
            return contactsRepository.getAllContact(
                page = page,
                pageSize = pageSize
            ).onEach { value ->
                contacts = ArrayList(value)
            }
        } else {
            return flowOf(contacts.filter { contact ->
                val score = FuzzySearch.ratio(contact.name.lowercase(), searchQuery.lowercase())
                return@filter (score > 60)
            })
        }
    }

    override fun getAllContact(): Flow<List<Contact>> {
        return contactsRepository.getAllContact()
    }

    override suspend fun createContact(contact: Contact) {
        return contactsRepository.insertContact(contact.toEntity())
    }
}