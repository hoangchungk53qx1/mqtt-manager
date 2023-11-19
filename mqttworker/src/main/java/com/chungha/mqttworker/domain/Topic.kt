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

/**
 *  QOS_0(0), At most once, may be repeated or lost
 *
 *  QOS_1(1), At least once, may be repeated
 *
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