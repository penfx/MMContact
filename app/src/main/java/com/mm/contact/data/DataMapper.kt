package com.mm.contact.data

import com.mm.contact.data.local.entities.ContactEntity
import com.mm.contact.domain.model.Contact

fun ContactEntity.toModel() = Contact(id, name, phoneNumber, email)

fun Contact.toEntity() = ContactEntity(id?:0, name,phoneNumber, email)