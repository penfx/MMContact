package com.mm.contact.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.contact.domain.usecase.ContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var contactUseCase: ContactUseCase,
) : ViewModel() {

    val contacts = viewModelScope.launch {
        contactUseCase.getAllContact(100,1,"")
    }

}