package com.chungha.mqtt_manager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.chungha.mqtt_manager.databinding.ActivityMainBinding
import com.chungha.mqttworker.MqttConnectStatus
import com.chungha.mqttworker.MqttManager
import com.chungha.mqttworker.domain.MqttMessage
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnConnect.setOnClickListener {
            viewModel.connectFirstTimes(
                context = this,
                host = binding.inputHost.text.toString(),
                userName = binding.inputUserName.text.toString(),
                password = binding.inputPassword.text.toString()
            )
        }

        binding.btnSubscribeTopic.setOnClickListener {
            viewModel.subscribeTopic(
                topic = binding.inputTopic.text.toString()
            )
        }

        MqttManager.messageState
            .onEach(::renderMessage)
            .launchIn(lifecycleScope)


        MqttManager.connectStatusState
            .onEach(::renderStatus)
            .launchIn(lifecycleScope)
    }

    private fun renderMessage(message: MqttMessage) {
        if (message.topic == null || message.message == null) return
        binding.tvMessage.text = message.message
    }

    private fun renderStatus(status: MqttConnectStatus) {
        binding.tvStatusConnection.text = getString(R.string.status_name, status)
    }

}