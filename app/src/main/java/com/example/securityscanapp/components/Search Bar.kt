package com.example.securityscanapp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(modifier: Modifier = Modifier,
              query : String = "",
              onQueryChange : (String) -> Unit)
{
    val keyboardcontroller = LocalSoftwareKeyboardController.current

    Row(modifier = modifier) {
        OutlinedTextField(
            value = query,
            onValueChange = { onQueryChange(it) },
            modifier = Modifier.weight(1f).padding(16.dp).height(70.dp),
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon" , modifier = Modifier.padding(horizontal = 20.dp)) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("")}) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear Text")
                    }
                }
            },
            shape = CircleShape,
            placeholder = { Text("Search...") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                keyboardcontroller?.hide() //hides keyboard when a search is performed
                onQueryChange(query)
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}