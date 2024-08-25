@file:OptIn(ExperimentalMaterial3Api::class)

package com.mm.contact.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mm.contact.R
import com.mm.contact.presentation.theme.MContactsTheme
import com.mm.contact.presentation.viewmodels.MainViewModel
import com.mm.contact.presentation.views.composables.contact.ContactEvent
import com.mm.contact.presentation.views.composables.contact.create.CreateContactDialog
import com.mm.contact.presentation.views.composables.contact.list.ListContactScreen
import com.mm.contact.presentation.views.composables.contact.list.SearchBox
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MContactsTheme {
                TextInputAndListViewScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TextInputAndListViewScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Contacts") }, actions = {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = Color.Green,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    onClick = {
                        viewModel.onEvent(ContactEvent.OpenDialogAddContact(true))
                    }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                }
            })
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                //Dialog
                CreateContactDialog(viewModel = viewModel)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SearchBox(focusManager = focusManager, viewModel = viewModel)
                    ListContactScreen(viewModel = viewModel)
                }
            }
        })
}