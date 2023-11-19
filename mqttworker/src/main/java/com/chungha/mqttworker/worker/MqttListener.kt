package com.chungha.mqttworker.worker

import android.content.Context
import com.chungha.mqttworker.domain.MqttClientOptions
import com.chungha.mqttworker.domain.Topic

interface MqttListener {

    /**
     * MQTT Connect
     * @param context Context
     * @param options MqttClientOptions
     */
    fun connect(context: Context, options: MqttClientOptions)


    /**
     * MQTT Subscribe List Topic
     * @param topics List<Topic>
     */
    fun subscribeListTopic(topics: List<Topic>)

    /**
     * MQTT Subscribe List Topic
     * @param topics List<Topic>
     */
    fun unSubscribeListTopic(topics: List<Topic>)

    /**
     * MQTT Disconnected
     */
    fun disconnect()

}