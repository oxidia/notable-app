package com.example.notable.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notable.ui.theme.RedOrange
import com.example.notable.ui.theme.RedPink

@Composable
fun ColorCircle(
    color: Color,
    onClick: (Color) -> Unit,
    selected: Boolean = false,
    modifier: Modifier = Modifier,
) {

    val borderColor = when (selected) {
        true -> MaterialTheme.colorScheme.onPrimary
        false -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(50.dp)
            .shadow(15.dp, CircleShape)
            .clip(CircleShape)
            .clickable {
                onClick(color)
            }
            .background(
                color = color,
                shape = CircleShape
            )
            .border(
                width = 4.dp,
                color = borderColor,
                shape = CircleShape
            )
            .then(modifier)
    )
}

@Preview(showBackground = true)
@Composable
fun ColorCirclePreview() {
    Row {
        ColorCircle(
            color = RedOrange,
            selected = false,
            onClick = {}
        )

        ColorCircle(
            color = RedPink,
            selected = false,
            onClick = {}
        )
    }
}
