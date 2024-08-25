package com.mm.contact.presentation.common

sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()
    data class Success<out T>(val value: T?) : ViewState<T>()
}