package com.example.notable.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notable.feature_note.domain.model.Note
import com.example.notable.feature_note.domain.use_case.NoteUseCases
import com.example.notable.feature_note.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())

    val state: State<NotesState> = _state

    private var lastDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(state.value.notesOrder)
    }

    fun onEvent(event: NotesUIEvent) {
        when (event) {
            is NotesUIEvent.OnNoteClick -> {}

            is NotesUIEvent.DeleteNote -> {
                viewModelScope.launch {
                    lastDeletedNote = event.note
                    notesUseCases.deleteNote(event.note)
                }
            }

            is NotesUIEvent.RestoreNote -> {
                lastDeletedNote?.let { note ->
                    viewModelScope.launch {
                        notesUseCases.addNote(note)
                        lastDeletedNote = null
                    }
                }
            }

            is NotesUIEvent.Order -> {
                if (state.value.notesOrder::class == event.noteOrder::class
                    && state.value.notesOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                _state.value = _state.value.copy(
                    notesOrder = event.noteOrder
                )

                getNotes(event.noteOrder)
            }

            is NotesUIEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSelectionVisible = !state.value.isOrderSelectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()

        getNotesJob = notesUseCases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = _state.value.copy(
                    notes = notes
                )
            }
            .launchIn(viewModelScope)
    }
}
