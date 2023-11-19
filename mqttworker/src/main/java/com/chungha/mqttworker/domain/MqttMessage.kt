package com.chungha.mqttworker.domain

data class MqttMessage(
    val topic: String? = null,
    val message: String? = null,
)
