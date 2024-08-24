@file:OptIn(ExperimentalMaterial3Api::class)

package com.mm.contact.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mm.contact.presentation.theme.MContactsTheme

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
fun TextInputAndListViewScreen() {
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    val items = remember { mutableStateListOf<String>("Item 1", "Item 2", "Item 3") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Text Input & List View") })
        },
        content = { _ ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Text Input
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text(text = "Enter Text") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Add Button
                Button(
                    onClick = {
                        if (inputText.text.isNotBlank()) {
                            items.add(inputText.text)
                            inputText = TextFieldValue("") // Clear the input field
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add")
                }

                // List View
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { item ->
                        Text(item)
                    }
                }
            }
        })
}