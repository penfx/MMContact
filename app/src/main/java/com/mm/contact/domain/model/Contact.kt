package com.mm.contact.domain.model

data class Contact(
    var id: Int? = null,
    var name: String,
    var phoneNumber: String?,
    var email: String?
)