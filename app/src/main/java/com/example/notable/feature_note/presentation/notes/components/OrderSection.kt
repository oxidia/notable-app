package com.example.notable.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notable.feature_note.domain.util.NoteOrder
import com.example.notable.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder,
    onOrderChange: (NoteOrder) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OrderRadioButton(label = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelectedChange = {
                    onOrderChange(NoteOrder.Title(noteOrder.orderType))
                })

            Spacer(modifier = Modifier.width(8.dp))

            OrderRadioButton(label = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelectedChange = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                })
            Spacer(modifier = Modifier.width(8.dp))

            OrderRadioButton(label = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelectedChange = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                })
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OrderRadioButton(label = "Asc",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelectedChange = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                })

            Spacer(modifier = Modifier.width(8.dp))

            OrderRadioButton(label = "Desc",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelectedChange = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                })
        }
    }
}
