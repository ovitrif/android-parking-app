package com.parkingapp.core

interface Action<T> {

    fun call(t: T)
}
