package com.mm.contact.presentation.views.composables.contact.create

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mm.contact.R
import com.mm.contact.domain.model.Contact
import com.mm.contact.presentation.viewmodels.MainViewModel
import com.mm.contact.presentation.views.composables.contact.ContactEvent
import com.mm.contact.util.Tags

@Composable
fun CreateContactDialog(
    viewModel: MainViewModel
) {
    if (viewModel.state.value.isOpenDialogAddContact) {
        Dialog(onDismissRequest = { viewModel.onEvent(ContactEvent.OpenDialogAddContact(false)) }) {
            val inputName = rememberSaveable { mutableStateOf("") }
            val inputEmail = rememberSaveable { mutableStateOf("") }
            val inputPhoneNumber = rememberSaveable { mutableStateOf("") }
            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .padding(5.dp)
                    .testTag(Tags.CREATE_CONTACT_DIALOG),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = stringResource(R.string.create_contact),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = inputName.value,
                        maxLines = 1,
                        onValueChange = { inputName.value = it },
                        placeholder = { Text(text = stringResource(id = R.string.enter_name)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = inputPhoneNumber.value,
                        maxLines = 1,
                        onValueChange = { inputPhoneNumber.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        placeholder = { Text(text = stringResource(id = R.string.enter_phone_number)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    TextField(
                        value = inputEmail.value,
                        maxLines = 1,
                        onValueChange = { inputEmail.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        placeholder = { Text(text = stringResource(id = R.string.enter_email)) },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(ContactEvent.OpenDialogAddContact(false))
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .padding(10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.close),
                                color = Color.White
                            )
                        }
                        Button(
                            enabled = inputName.value.isNotEmpty() && inputPhoneNumber.value.isNotEmpty(),
                            onClick = {
                                viewModel.onEvent(ContactEvent.OpenDialogAddContact(false))
                                viewModel.onEvent(ContactEvent.CreateContact(
                                    Contact(
                                        name = inputName.value,
                                        phoneNumber = inputPhoneNumber.value,
                                        email = inputEmail.value
                                    ))
                                )
                            },
                            modifier = Modifier
                                .height(60.dp)
                                .padding(10.dp, 10.dp, 0.dp, 10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

}