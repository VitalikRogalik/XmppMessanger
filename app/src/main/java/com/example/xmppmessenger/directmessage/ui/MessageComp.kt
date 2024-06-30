package com.example.xmppmessenger.directmessage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.xmppmessenger.directmessage.ui.model.MessageItem

@Composable
fun MessageComp(
    messageItem: MessageItem
) {
    Box(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .align(
                    if (messageItem.isFromMe) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    }
                )
                .background(
                    color = if (messageItem.isFromMe) {
                        Color.Blue.copy(alpha = 0.7f)
                    } else {
                        Color.LightGray
                    },
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            text = messageItem.text,
            color = if (messageItem.isFromMe){
                Color.White
            } else {
                Color.Black
            }
        )
    }

}
