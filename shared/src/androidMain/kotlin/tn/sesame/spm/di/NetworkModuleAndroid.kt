package tn.sesame.spm.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val networkModule : Module = module {
    factory(RestClientImplTag) {
        HttpClient(OkHttp) {
            defaultRequest {
                host = DEFAULT_HOST
                port = DEFAULT_PORT
            }
            install(Logging)
            install(HttpTimeout){
                requestTimeoutMillis = 10000
            }
            install(ContentNegotiation) {
                json(Json {
                    isLenient = false
                    prettyPrint = true
                    allowStructuredMapKeys = true
                })
            }
        }
    }
}