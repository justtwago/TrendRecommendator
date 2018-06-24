@file:JvmName("Consume")

package com.example.artyomvlasov.trendrecommendator.app.utils.extensions

fun consume(action: () -> Unit): Boolean {
    action()
    return true
}