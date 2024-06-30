package com.example.xmppmessenger.data.xmpp

import com.example.xmppmessenger.FromToObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.net.ssl.SSLSocketFactory

class SmackConnectionManager {

    private var xmppConnection: XMPPTCPConnection? = null

    suspend fun init() {

        val connection = buildConnection(buildConfiguration())

        connectAndLogin(connection = connection)

        xmppConnection = connection.also {
            MessagesManager.init(it)
        }

        /*val connectionResult = connectAndLogin(connection)

        this@SmackConnectionManager.connection = if (connectionResult.isSuccess) {
            connection.also {
                MessagesManager.init(it)
            }
        } else {
            null
        }*/
    }

    private fun buildConfiguration(): XMPPTCPConnectionConfiguration {

        return XMPPTCPConnectionConfiguration.builder()
            .setPort(5222)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.required)
            .setSocketFactory(SSLSocketFactory.getDefault())
            .setUsernameAndPassword(FromToObject.from, "1234")
            .setXmppDomain("jabber.hot-chilli.net")
            .setHost("jabber.hot-chilli.net")
            .setSendPresence(false)
        .build()

    }

    private fun buildConnection(configuration: XMPPTCPConnectionConfiguration): XMPPTCPConnection {
        return XMPPTCPConnection(configuration)
    }

    private suspend fun connectAndLogin(connection: XMPPTCPConnection) =
        with(connection) {
            withContext(Dispatchers.IO) {
                connect()
                login()
            }
        }
}
