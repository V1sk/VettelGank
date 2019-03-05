package com.cjw.vettelgank.data.source.request

interface Request<out T> {
    fun request(): T
}