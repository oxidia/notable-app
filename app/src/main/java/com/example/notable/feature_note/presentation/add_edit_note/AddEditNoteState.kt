package com.example.notable.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.Color

data class AddEditNoteState(
    val title: NoteTextFieldState,
    val content: NoteTextFieldState,
    val color: Color,
)
