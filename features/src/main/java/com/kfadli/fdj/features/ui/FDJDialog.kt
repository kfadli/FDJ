package com.kfadli.fdj.features.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FDJPopupDialog(
    content: String,
    textSubmit: String? = null,
    onCancel: () -> Unit,
    onConfirm: (() -> Unit)? = null,
) = AlertDialog(
    modifier =
        Modifier
            .fillMaxWidth()
            .shadow(elevation = 8.dp),
    containerColor = Color.White,
    shape = MaterialTheme.shapes.medium,
    onDismissRequest = onCancel,
    text = {
        Text(
            text = content,
            fontSize = 18.sp,
        )
    },
    dismissButton = {
        Button(onClick = onCancel) {
            Text(
                text = "Cancel",
                fontSize = 18.sp,
            )
        }
    },
    confirmButton = {
        onConfirm?.let {
            Button(onClick = it) {
                Text(
                    text = textSubmit ?: "OK",
                    fontSize = 18.sp,
                )
            }
        }
    },
)

@Composable
fun FDJLoadingDialog() =
    Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .size(100.dp)
                    .background(color = Color.White, shape = MaterialTheme.shapes.medium),
        ) {
            CircularProgressIndicator()
        }
    }
