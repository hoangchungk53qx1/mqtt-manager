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

package com.chungha.mqttworker.worker

import android.content.Context
import com.chungha.mqttworker.MqttConnectStatus.*
import com.chungha.mqttworker.MqttManager
import com.chungha.mqttworker.domain.MqttClientOptions
import com.chungha.mqttworker.domain.Topic
import info.mqtt.android.service.MqttAndroidClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.ref.WeakReference

internal class MqttWorkerListener : MqttListener {

    companion object {
        /** Create instance Mqtt worker */
        val instance: MqttWorkerListener by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MqttWorkerListener()
        }
    }

    /** MQTT ClientAndroid */
    private var mClient: MqttAndroidClient? = null

    /** MQTT Config Options */
    private var mOptions: MqttClientOptions? = null

    /** Context */
    private var weakContext: WeakReference<Context> = WeakReference(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            MqttManager.connectStatusState.collect { status ->
                if (status == CONNECTED) {
                    mClient?.subscribeTopic()
                }
            }
        }
    }

    override fun connect(context: Context, options: MqttClientOptions) {
        weakContext = WeakReference(context)
        mOptions = options
        doConnect()
    }

    override fun subscribeListTopic(topics: List<Topic>) {
        try {
            if (MqttManager.currentStatusConnect == CONNECTED) {
                // Get topic and qos
                val topic = topics.map { it.topic }.toTypedArray()
                val qos = topics.map { it.qos.value }.toIntArray()

                mClient?.subscribe(
                    topic = topic,
                    qos = qos,
                    userContext = null,
                    callback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {

                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable?
                        ) {
                            exception?.printStackTrace()
                        }
                    })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun unSubscribeListTopic(topics: List<Topic>) {
        try {
            if (MqttManager.currentStatusConnect == CONNECTED) {
                mClient?.unsubscribe(
                    topic = topics.map { it.topic }.toTypedArray(),
                    userContext = null,
                    callback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {

                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable?
                        ) {
                            exception?.printStackTrace()
                        }
                    })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun disconnect() {
        mClient?.apply {
            if (isConnected) {
                disconnect()
                MqttManager.updateStatusConnect(DISCONNECTED)
            }
        }
        mClient?.unregisterResources()
        mClient?.close()
        mClient = null
        mOptions = null
    }

    private fun doConnect() {
        // If it is already connected or a previous connection task is connecting, the connection will not be repeated.
        if (MqttManager.currentStatusConnect != DISCONNECTED) return
        // If context does not exist, do not connect
        val context = weakContext.get() ?: return
        // If the configuration does not exist, do not connect
        val options = mOptions ?: return
        //Change MQTT status
        MqttManager.updateStatusConnect(CONNECTING)
        // Disconnect old connection
        disconnect()
        mOptions = options
        val clientId = "${options.clientId}_${System.currentTimeMillis()})}"
        try {
            //Create MQTT client
            mClient = MqttAndroidClient(context, options.host, clientId).apply {
                //Set MQTT connection configuration
                val connectOptions = options.toMqttConnectOptions()
                //Set callback information monitoring
                setCallback(mCallBack)
                // establish connection
                connect(connectOptions, null, mConnectListener)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Callback information monitoring
     */
    private val mCallBack = object : MqttCallbackExtended {
        override fun connectionLost(cause: Throwable?) {
            MqttManager.updateStatusConnect(status = CONNECTING)
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            message ?: return
            topic ?: return
            MqttManager.receiveMessage(topic, message.toString())
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            token ?: return
        }

        override fun connectComplete(reconnect: Boolean, serverURI: String?) {
            MqttManager.updateStatusConnect(status = CONNECTED)
        }
    }

    /**
     * Update Status Connect
     */
    private val mConnectListener = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            MqttManager.updateStatusConnect(status = CONNECTED)
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            MqttManager.updateStatusConnect(status = CONNECTING)
        }
    }

    /**
     * subscribeTopic First after connect
     * @receiver MqttAndroidClient
     */
    private fun MqttAndroidClient.subscribeTopic() {
        mOptions?.topics?.forEach { topic ->
            try {
                subscribe(
                    topic = topic.topic,
                    qos = topic.qos.value,
                    userContext = null,
                    callback = object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {

                        }

                        override fun onFailure(
                            asyncActionToken: IMqttToken?,
                            exception: Throwable?
                        ) {
                            exception?.printStackTrace()
                        }
                    })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}