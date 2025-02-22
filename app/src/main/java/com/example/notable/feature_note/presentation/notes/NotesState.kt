package com.example.notable.feature_note.presentation.notes

import com.example.notable.feature_note.domain.model.Note
import com.example.notable.feature_note.domain.util.NoteOrder
import com.example.notable.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val notesOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible: Boolean = false
)
