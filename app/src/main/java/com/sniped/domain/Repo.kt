package com.sniped.domain

interface Repo<T> {

    val isSet: Boolean

    fun get(): T

    fun set(request: T)

    fun clear()
}
