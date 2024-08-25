package com.mm.contact.presentation.views.composables.contact.create

data class CreateContactState(
    val isOpenDialogAddContact: Boolean = false,
    var query:String ="",
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = ""
)