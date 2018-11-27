package com.parkingapp.domain

interface Repo<T> {

    val isSet: Boolean

    fun get(): T

    fun set(request: T)

    fun clear()
}
