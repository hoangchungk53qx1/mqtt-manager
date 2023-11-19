package com.chungha.mqttworker

import android.content.Context
import com.chungha.mqttworker.domain.MqttClientOptions
import com.chungha.mqttworker.domain.MqttMessage
import com.chungha.mqttworker.domain.Topic
import com.chungha.mqttworker.worker.MqttListener
import com.chungha.mqttworker.worker.MqttWorkerListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * MQTT Manager
 */
object MqttManager : MqttListener {

    // Connection Status
    private val connectStatusFlow = MutableStateFlow(value = MqttConnectStatus.DISCONNECTED)

    // Get Message from callback client
    private val messageFlow = Channel<MqttMessage>(capacity = Channel.UNLIMITED)

    // Scope run coroutine
    private val receiveScope = CoroutineScope(context = Dispatchers.Main + SupervisorJob())

    /**
     * Update MQTT connection status
     * @param status MqttConnectStatus
     */
    internal fun updateStatusConnect(status: MqttConnectStatus) {
        connectStatusFlow.update {
            status
        }
    }

    /**
     * Get the current MQTT connection status
     * @return MqttConnectStatus
     */
    val currentStatusConnect: MqttConnectStatus
        get() = connectStatusFlow.value

    /**
     * Get MQTT connection status Flow
     * @return StateFlow<MqttConnectStatus>
     */
    val connectStatusState = connectStatusFlow.asStateFlow()

    /**
     * Receive subscription messages
     * @param topic String
     * @param message String
     */
    internal fun receiveMessage(topic: String, message: String) {
        messageFlow.trySend(element = MqttMessage(topic = topic, message = message))
    }

    /**
     * Get subscription message Flow from callback
     * @return StateFlow<MqttMessage>
     */
    val messageState: StateFlow<MqttMessage>
        get() = messageFlow
            .receiveAsFlow()
            .stateIn(
                scope = receiveScope,
                started = SharingStarted.Eagerly,
                initialValue = MqttMessage("", "")
            )

    /**
     * Connect to MQTT
     * @param context Context
     * @param options MqttClientOptions
     */
    override fun connect(context: Context, options: MqttClientOptions) {
        MqttWorkerListener.instance.connect(
            context = context,
            options = options
        )
    }

    /**
     * subscribe to MQTT
     * @param topics List<Topic>
     */
    override fun subscribeListTopic(topics: List<Topic>) {
        MqttWorkerListener.run { instance.subscribeListTopic(topics = topics) }
    }

    /**
     * unsubscribe to MQTT
     * @param topics List<Topic>
     */
    override fun unSubscribeListTopic(topics: List<Topic>) {
        MqttWorkerListener.run { instance.unSubscribeListTopic(topics = topics) }
    }

    /**
     * Disconnect
     */
    override fun disconnect() {
        MqttWorkerListener.instance.disconnect()
    }

}