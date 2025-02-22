package com.example.notable.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notable.feature_note.domain.model.Note
import com.example.notable.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = mutableStateOf(
        AddEditNoteState(
            color = Note.noteColors.random(),
            title = NoteTextFieldState(
                hint = "Enter title..."
            ),
            content = NoteTextFieldState(
                hint = "Enter some content..."
            ),
        )
    )

    val state: State<AddEditNoteState> = _state

    private var currentNoteId: Int? = null

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<Int>("noteId")
            ?.let { noteId ->
                if (noteId != -1) {
                    viewModelScope.launch {
                        notesUseCases.getNote(noteId)
                            ?.let { note ->
                                currentNoteId = note.id

                                _state.value = state.value.copy(
                                    title = state.value.title.copy(
                                        text = note.title,
                                        isHintVisible = false
                                    ),
                                    content = state.value.content.copy(
                                        text = note.content,
                                        isHintVisible = false
                                    ),
                                    color = Color(note.color),
                                )
                            }
                    }
                }
            }
    }

    fun onEvent(event: AddEditNoteUIEvent) {
        when (event) {
            is AddEditNoteUIEvent.OnSaveClick -> {
                saveNote()
            }

            is AddEditNoteUIEvent.OnTitleValueChange -> {
                _state.value = state.value.copy(
                    title = state.value.title.copy(
                        text = event.value
                    )
                )
            }

            is AddEditNoteUIEvent.OnTitleFocusChange -> {
                _state.value = state.value.copy(
                    title = state.value.title.copy(
                        isHintVisible = !event.focusState.isFocused && state.value.title.text.isBlank()
                    )
                )
            }

            is AddEditNoteUIEvent.OnContentValueChange -> {
                _state.value = state.value.copy(
                    content = state.value.content.copy(
                        text = event.value
                    )
                )
            }

            is AddEditNoteUIEvent.OnContentFocusChange -> {
                _state.value = state.value.copy(
                    content = state.value.content.copy(
                        isHintVisible = !event.focusState.isFocused && state.value.content.text.isBlank()
                    )
                )
            }

            is AddEditNoteUIEvent.OnColorValueChange -> {
                _state.value = state.value.copy(
                    color = event.value
                )
            }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            try {
                notesUseCases.addNote.invoke(
                    Note(
                        id = currentNoteId,
                        title = _state.value.title.text,
                        content = _state.value.content.text,
                        timestamp = System.currentTimeMillis(),
                        color = _state.value.color.toArgb()
                    )
                )
                _eventFlow.emit(Event.SaveNote)
            } catch (e: Exception) {
                _eventFlow.emit(
                    Event.ShowSnackbar(
                        message = e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }

    sealed class Event {
        data class ShowSnackbar(val message: String) : Event()
        data object SaveNote : Event()
    }
}
