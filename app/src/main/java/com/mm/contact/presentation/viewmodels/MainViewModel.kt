package com.mm.contact.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mm.contact.domain.usecase.ContactUseCase
import com.mm.contact.presentation.common.ViewState
import com.mm.contact.presentation.views.composables.contact.ContactEvent
import com.mm.contact.presentation.views.composables.contact.create.CreateContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var contactUseCase: ContactUseCase,
) : ViewModel() {
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
                    contactUseCase.createContact(event.contact)
                }
            }

            is ContactEvent.TypeSearch -> {
                _state.value = state.value.copy(query = event.input)
                onEvent(ContactEvent.FetchContact())
            }

            is ContactEvent.FetchContact -> {
                viewModelScope.launch {
                    val query: String? = _state.value.query.ifEmpty {
                        null
                    }
                    contactUseCase.getAllContact(1,1,query).collect {
                        viewStateMutable.value = ViewState.Success(it)
                    }
                }

            }
        }
    }

}