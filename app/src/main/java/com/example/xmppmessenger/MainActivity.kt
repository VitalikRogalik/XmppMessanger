package com.example.xmppmessenger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.xmppmessenger.core.ui.theme.XmppMessengerTheme
import com.example.xmppmessenger.data.xmpp.SmackConnectionManager
import com.example.xmppmessenger.directmessage.DirectMessageViewModel
import com.example.xmppmessenger.directmessage.ui.DirectMessageScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val smackConnectionManager = SmackConnectionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            smackConnectionManager.init()
        }

        setContent {
            XmppMessengerTheme {
                DirectMessageScreen(
                    viewModel = DirectMessageViewModel()
                )
            }
        }
    }
}
