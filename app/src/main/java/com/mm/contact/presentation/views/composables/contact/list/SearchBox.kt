package com.mm.contact.presentation.views.composables.contact.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mm.contact.R
import com.mm.contact.presentation.viewmodels.MainViewModel
import com.mm.contact.presentation.views.composables.contact.ContactEvent

@Composable
fun SearchBox(focusManager: FocusManager, viewModel: MainViewModel) {
    TextField(
        value = viewModel.state.value.query,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = {
            viewModel.onEvent(ContactEvent.FetchContact())
            focusManager.clearFocus()
        }),
        onValueChange = {
            viewModel.onEvent(ContactEvent.TypeSearch(it))
        },
        placeholder = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
    )

}