package com.mm.contact.presentation.views.composables.contact

import com.mm.contact.domain.model.Contact

sealed class ContactEvent {
    class OpenDialogAddContact(val isOpen: Boolean) : ContactEvent()
    class TypeSearch(val input: String) : ContactEvent()
    class CreateContact(val contact: Contact) : ContactEvent()
    class FetchContact():ContactEvent()
}