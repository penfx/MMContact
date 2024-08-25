package com.mm.contact.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.contact.domain.model.Contact
import com.mm.contact.domain.usecase.ContactUseCase
import com.mm.contact.presentation.common.ViewState
import com.mm.contact.presentation.views.composables.contact.ContactEvent
import com.mm.contact.presentation.views.composables.contact.create.CreateContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var contactUseCase: ContactUseCase,
) : ViewModel() {
    private var contacts = mutableListOf<Contact>()
    private var nextPage = 0;
    private var isSearching = false;
    private var isLoadingMoreContact = false;
    private val viewStateMutable: MutableStateFlow<ViewState<*>> =
        MutableStateFlow(ViewState.Loading)
    val viewState = viewStateMutable.asStateFlow()
    private val _state = mutableStateOf(CreateContactState())
    val state: State<CreateContactState> = _state

    fun onEvent(event: ContactEvent) {
        when (event) {
            is ContactEvent.OpenDialogAddContact -> {
                _state.value = state.value.copy(
                    isOpenDialogAddContact = event.isOpen
                )
            }

            is ContactEvent.CreateContact -> {
                _state.value = state.value.copy()
                viewModelScope.launch(Dispatchers.IO) {
                    contactUseCase.createContact(event.contact).also {
                        onEvent(ContactEvent.FetchContact())
                    }
                }
            }

            is ContactEvent.TypeSearch -> {
                _state.value = state.value.copy(query = event.input)
                isSearching = event.input.isNotEmpty()
                onEvent(ContactEvent.SearchContact())
            }

            is ContactEvent.FetchContact -> {
                viewModelScope.launch {
                    contactUseCase.getContacts(0).collect {
                        nextPage = 0
                        viewStateMutable.value = ViewState.Success(it)
                        contacts = it.toMutableList()
                        //Get for caching search
                        contactUseCase.getAllContact().collect {}
                    }
                }

            }

            is ContactEvent.SearchContact -> {
                viewModelScope.launch {
                    val query: String? = _state.value.query.ifEmpty {
                        null
                    }
                    contactUseCase.searchContacts(query = query).collect {
                        contacts = it.toMutableList()
                        viewStateMutable.value = ViewState.Success(it)
                    }
                }

            }

            is ContactEvent.LoadMoreContact -> {
                if (isSearching) return
                if (isLoadingMoreContact) return

                isLoadingMoreContact = true

                viewModelScope.launch {
                    delay(200)
                    nextPage += 1
                    contactUseCase.getContacts(nextPage).collect {
                        isLoadingMoreContact = true // Re-enable the button after the delay
                        contacts.addAll(it)
                        viewStateMutable.value = ViewState.Success(contacts)
                        isLoadingMoreContact = false
                    }
                }

            }
        }
    }

}