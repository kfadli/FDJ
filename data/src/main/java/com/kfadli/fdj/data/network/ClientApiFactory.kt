package com.kfadli.fdj.data.network

import com.kfadli.fdj.data.di.THE_SPORTS_DB
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequest
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class ClientApiFactory(
    private val engine: HttpClientEngine?,
    private val json: Json,
) {
    fun create(type: String): HttpClient =
        when (type) {
            THE_SPORTS_DB -> {
                configure()
            }

            else -> configure()
        }

    private fun configure() =
        httpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Timber.d("HttpClient - $message")
                        }
                    }
            }
            install(HttpCache)

            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_DELAY
                connectTimeoutMillis = TIMEOUT_DELAY
                socketTimeoutMillis = TIMEOUT_DELAY
            }

            install(ContentNegotiation) {
                json(
                    json = json,
                    contentType = ContentType.Any,
                )
            }
            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause: Throwable, request: HttpRequest ->
                    Timber.w(
                        message = "Failed to handle response request: $request",
                        t = cause,
                    )
                    throw cause
                }
            }
        }

    private fun httpClient(block: HttpClientConfig<*>.() -> Unit) =
        when (engine) {
            null -> HttpClient { block() }
            else -> HttpClient(engine) { block() }
        }

    companion object {
        private const val TIMEOUT_DELAY = 30000L
    }
}
