package com.mm.contact.data

import com.mm.contact.data.entities.ContactEntity
import com.mm.contact.domain.model.Contact

fun ContactEntity.toModel() = Contact(name, phoneNumber, email)

fun Contact.toEntity() = ContactEntity(name,phoneNumber, email)