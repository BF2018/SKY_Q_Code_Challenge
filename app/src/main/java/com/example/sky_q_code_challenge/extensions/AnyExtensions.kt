package com.example.sky_q_code_challenge.extensions

import com.example.sky_q_code_challenge.ui.MovieApp
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers




inline fun <T> Observable<T>.runApiCall(crossinline onSuccess: ((t: T) -> Unit), crossinline onError: ((throwable: Throwable) -> Unit)): Disposable {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .retry(1)
        .subscribe({
            onSuccess(it)
        }, {
            onError(it)
        })
}

fun getAppContext() = MovieApp.getInstance()?.applicationContext