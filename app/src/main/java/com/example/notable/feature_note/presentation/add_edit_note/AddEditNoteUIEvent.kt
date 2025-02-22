package com.example.notable.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color


sealed class  AddEditNoteUIEvent {
    data object OnSaveClick : AddEditNoteUIEvent()
    data class OnTitleValueChange(val value: String) : AddEditNoteUIEvent()
    data class OnTitleFocusChange(val focusState: FocusState) : AddEditNoteUIEvent()
    data class OnContentValueChange(val value: String) : AddEditNoteUIEvent()
    data class OnContentFocusChange(val focusState: FocusState) : AddEditNoteUIEvent()
    data class OnColorValueChange(val value: Color) : AddEditNoteUIEvent()
}
