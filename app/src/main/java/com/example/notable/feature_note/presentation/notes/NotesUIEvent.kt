package com.example.notable.feature_note.presentation.notes

import com.example.notable.feature_note.domain.model.Note
import com.example.notable.feature_note.domain.util.NoteOrder

sealed class NotesUIEvent {
    data class OnNoteClick(val note: Note) : NotesUIEvent()
    data class DeleteNote(val note: Note) : NotesUIEvent()
    data class Order(val noteOrder: NoteOrder) : NotesUIEvent()
    data object RestoreNote : NotesUIEvent()
    data object ToggleOrderSection : NotesUIEvent()
}
