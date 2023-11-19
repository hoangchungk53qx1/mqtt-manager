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

package com.chungha.mqtt_manager

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chungha.mqttworker.MqttManager
import com.chungha.mqttworker.domain.MqttClientOptions
import com.chungha.mqttworker.domain.Topic
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class MainViewModel : ViewModel() {
    init {
        MqttManager.messageState
            .onEach {
                Timber.tag(TAG).d("MessageState > ${it.message}")
            }.launchIn(viewModelScope)

        MqttManager.connectStatusState
            .onEach {
                Timber.tag(TAG).d("StatusConnect > ${it.name}")
            }.launchIn(viewModelScope)
    }

    fun connectFirstTimes(
        context: Context,
        host: String,
        userName: String,
        password: String
    ) {
        MqttManager.connect(
            context = context,
            options = MqttClientOptions(
                username = userName,
                passwords = password,
                host = host,
                topics = listOf(Topic("firstTopic", Topic.Qos.QOS_2))
            )
        )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}