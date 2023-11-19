package com.chungha.mqttworker.domain

import org.eclipse.paho.client.mqttv3.MqttConnectOptions

/**
 * MQTT client configuration
 */
data class MqttClientOptions(
    val clientId: String = "client_android_id",
    val host: String,
    val username: String,
    val passwords: String,
    val isSessionClean: Boolean = false,
    val connectTimeout: Int = 30,
    val keepAliveIntervals: Int = 20,
    val autoReconnectInterval: Int = 10,
    val topics: List<Topic> = emptyList(),
) {
    fun toMqttConnectOptions() = MqttConnectOptions().apply {
        this.userName = username
        this.password = passwords.toCharArray()
        this.isCleanSession = isSessionClean
        this.connectionTimeout = connectTimeout
        this.keepAliveInterval = keepAliveIntervals
        this.isAutomaticReconnect = true
    }
}
