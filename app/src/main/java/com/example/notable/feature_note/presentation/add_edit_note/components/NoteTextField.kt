package com.example.notable.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isHintVisible: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: TextStyle = TextStyle(),
    modifier: Modifier = Modifier,
    onFocusChange: (FocusState) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .clickable {
                focusRequester.requestFocus()
            }
            .then(modifier),
    ) {
        BasicTextField(
            value = value,
            maxLines = maxLines,
            onValueChange = onValueChange,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEditScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Gray
            )
            .padding(10.dp)
    ) {
        NoteTextField(
            value = "",
            onValueChange = {},
            hint = "Title",
            isHintVisible = true,
            onFocusChange = {},
            textStyle = MaterialTheme.typography.headlineSmall,
        )

        NoteTextField(
            value = "",
            onValueChange = {},
            hint = "Content",
            isHintVisible = true,
            modifier = Modifier.weight(1f),
            onFocusChange = {},
            textStyle = MaterialTheme.typography.bodyMedium,
        )
    }
}
