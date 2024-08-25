package com.example.echocircleandroid.network;

import okhttp3.OkHttpClient
import okhttp3.Request
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import java.security.KeyStore

fun createUnsafeOkHttpClient(): OkHttpClient {
    // 신뢰할 수 있는 TrustManager 설정
    val trustAll = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            // No-op
        }

        override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
            // No-op
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
            return arrayOf()
        }
    })

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustAll, java.security.SecureRandom())

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAll[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
}
