/*
 * MIT License
 *
 * Copyright (c) 2023 Hoangchungk53qx1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
