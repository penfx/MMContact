package com.mm.contact.presentation.views.composables.contact.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mm.contact.domain.model.Contact
import com.mm.contact.presentation.common.ViewState
import com.mm.contact.presentation.viewmodels.MainViewModel
import com.mm.contact.presentation.views.composables.contact.ContactEvent
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Composable
fun ListContactScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state = viewModel.viewState.collectAsState(initial = ViewState.Loading)
    when (state.value) {
        is ViewState.Loading -> {
            Loading(
                viewModel
            )
        }

        is ViewState.Success<*> -> {
            ListContact(
                viewModel,
                (state.value as ViewState.Success<*>).value as List<Contact>
            )
        }
    }
}

@Composable
fun ListContact(viewModel: MainViewModel, contacts: List<Contact>) {
    val listState = rememberLazyListState()
    val canScrollForward: Boolean by remember { derivedStateOf { listState.canScrollForward } }
    val rageColors = mutableListOf(
        Blue,
        Color.Green,
        LightGray,
        Color.Red,
        Color.Green,
        Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.LightGray,
        Color.DarkGray,
        Color.Black
    )


    LazyColumn(state = listState) {
        items(contacts.size) { item ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(rageColors[Random.nextInt(rageColors.size)]),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${contacts[item].name[0]}".uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = contacts[item].name,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = contacts[item].phoneNumber ?: "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            LaunchedEffect(canScrollForward) {
                Log.e("WTF", "canScrollForward :$canScrollForward")
                if (!canScrollForward) {
                    viewModel.onEvent(ContactEvent.LoadMoreContact())
                }
            }
        }
    }
}

@Composable
fun Loading(viewModel: MainViewModel) {

    //Todo: create component loading
    viewModel.onEvent(ContactEvent.FetchContact())
}