package io.neomsoft.a4k_soft.presentation.notes

class SingleLiveEvent<T>(initValue: T) {

    var value: T? = initValue
        get() {
            val it = field
            field = null
            return it
        }
}