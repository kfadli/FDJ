package com.kfadli.fdj.features.extensions

import java.io.IOException

fun Throwable?.toStringError(): String =
    when (this) {
        is IOException -> "Internet error"
        else -> this?.message ?: "Unknown error"
    }
