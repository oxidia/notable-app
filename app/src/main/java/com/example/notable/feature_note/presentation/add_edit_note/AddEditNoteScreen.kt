package com.example.notable.feature_note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notable.feature_note.presentation.add_edit_note.components.ColorCircle
import com.example.notable.feature_note.presentation.add_edit_note.components.NoteTextField
import com.example.notable.ui.theme.BabyBlue
import com.example.notable.ui.theme.LightGreen
import com.example.notable.ui.theme.RedOrange
import com.example.notable.ui.theme.RedPink
import com.example.notable.ui.theme.Violet
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state = viewModel.state

    val snackbarHostState = remember { SnackbarHostState() }

    val colors = listOf(
        RedOrange, RedPink, BabyBlue, Violet, LightGreen
    )

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(state.value.color.value)
        )
    }

    LaunchedEffect(key1 = state.value.color.value) {
        noteBackgroundAnimatable.animateTo(
            targetValue = state.value.color,
            animationSpec = tween(
                durationMillis = 500
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.Event.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditNoteViewModel.Event.SaveNote -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel.onEvent(AddEditNoteUIEvent.OnSaveClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save note"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = noteBackgroundAnimatable.value
                )
                .statusBarsPadding()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                colors.forEach { color ->
                    ColorCircle(
                        color = color,
                        selected = color == state.value.color,
                        onClick = { currentColor ->
                            viewModel.onEvent(
                                AddEditNoteUIEvent.OnColorValueChange(
                                    currentColor
                                )
                            )
                        })
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            NoteTextField(
                value = state.value.title.text,
                hint = state.value.title.hint,
                isHintVisible = state.value.title.isHintVisible,
                textStyle = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                onFocusChange = {
                    viewModel.onEvent(
                        AddEditNoteUIEvent.OnTitleFocusChange(it)
                    )
                },
                onValueChange = { title ->
                    viewModel.onEvent(
                        AddEditNoteUIEvent.OnTitleValueChange(
                            title
                        )
                    )
                },
            )

            Spacer(modifier = Modifier.height(8.dp))

            NoteTextField(
                value = state.value.content.text,
                hint = state.value.content.hint,
                isHintVisible = state.value.content.isHintVisible,
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = { content ->
                    viewModel.onEvent(
                        AddEditNoteUIEvent.OnContentValueChange(
                            content
                        )
                    )
                },
                onFocusChange = {
                    viewModel.onEvent(
                        AddEditNoteUIEvent.OnContentFocusChange(it)
                    )
                },
            )
        }
    }
}
