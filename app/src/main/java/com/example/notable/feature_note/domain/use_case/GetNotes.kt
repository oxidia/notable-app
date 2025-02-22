package com.example.notable.feature_note.domain.use_case

import com.example.notable.feature_note.domain.model.Note
import com.example.notable.feature_note.domain.repository.NoteRepository
import com.example.notable.feature_note.domain.util.NoteOrder
import com.example.notable.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository,
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    ): Flow<List<Note>> {
        return repository.getNotes()
            .map { notes ->
                when (noteOrder) {
                    is NoteOrder.Title -> {
                        when (noteOrder.orderType) {
                            is OrderType.Ascending -> notes.sortedBy { it.title.lowercase() }
                            is OrderType.Descending -> notes.sortedByDescending { it.title.lowercase() }
                        }
                    }

                    is NoteOrder.Date -> {
                        when (noteOrder.orderType) {
                            is OrderType.Ascending -> notes.sortedBy { it.timestamp }
                            is OrderType.Descending -> notes.sortedByDescending { it.timestamp }
                        }
                    }

                    is NoteOrder.Color -> {
                        when (noteOrder.orderType) {
                            is OrderType.Ascending -> notes.sortedBy { it.color }
                            is OrderType.Descending -> notes.sortedByDescending { it.color }
                        }
                    }
                }
            }
    }
}
