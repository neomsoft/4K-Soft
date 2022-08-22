package io.neomsoft.a4k_soft.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun interval(timeMillis: Long) = flow {
    while (true) {
        emit(Unit)
        delay(timeMillis)
    }
}