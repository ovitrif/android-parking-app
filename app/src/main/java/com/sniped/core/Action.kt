package com.sniped.core

interface Action<T> {

    fun call(t: T)
}
