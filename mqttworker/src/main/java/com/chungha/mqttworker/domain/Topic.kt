package com.chungha.mqttworker.domain

/**
 *  QOS_0(0), At most once, may be repeated or lost
 *  QOS_1(1), At least once, may be repeated
 *  QOS_2(2), Only once, ensure that the message only arrives once (for stricter billing systems)
 */

data class Topic(
    val topic: String,
    val qos: Qos = Qos.QOS_0
) {
    enum class Qos(val value: Int) {
        QOS_0(0),
        QOS_1(1),
        QOS_2(2)
    }
}