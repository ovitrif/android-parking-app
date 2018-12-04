package com.parkingapp.extensions

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Subscribes to Observable with empty consumers.
 */
fun <T : Any> Observable<T>.subscribeEmpty(): Disposable = subscribe({}, {})
